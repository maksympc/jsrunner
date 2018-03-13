package com.jsrunner.server.services;

import com.jsrunner.server.models.ScriptExecutionItem;
import com.jsrunner.server.models.ScriptRequestDto;
import com.jsrunner.server.models.ScriptResponseDto;
import com.jsrunner.server.repository.MemoryStorage;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.*;

/**
 * This class contains the logic for handling JavaScript, correct processing and returning the result of the
 * sourceCode execution.
 *
 * @version 0.1
 */
@Service
public class ScriptExecutorService {
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private MemoryStorage db;
    private Map<UUID, Future> futureScriptExecutionResultMap;

    public ScriptExecutorService() {
        futureScriptExecutionResultMap = new ConcurrentSkipListMap<>();
    }

    @PostConstruct
    private void runHelper() {
        ScriptExecutionQueueHelperThread consumer = new ScriptExecutionQueueHelperThread();
        applicationContext.getAutowireCapableBeanFactory().autowireBean(consumer);
        consumer.start();
    }

    /**
     * @return Optional
     * TODO:Переделать дизайн метода
     */
    public Optional<Boolean> cancel(@NonNull UUID id) {
        // Updating db sourceCode item status
        Optional<ScriptExecutionItem> dbScriptItem = db.get(id);
        if (!dbScriptItem.isPresent()) {
            return Optional.empty();
        }
        dbScriptItem.get().setStatus(ScriptExecutionItem.ExecutionStatus.CANCELLED);
        // Cancelling already executing sourceCode item status
        Optional<Future> futureExecutionItem = Optional.of(futureScriptExecutionResultMap.get(id));
        return futureExecutionItem.map((item) -> item.cancel(true));
    }

    public Optional<ScriptResponseDto> add(@NonNull ScriptRequestDto scriptDto) {
        // Add sourceCode to storage
        Optional<UUID> scriptId = db.add(scriptDto.getSourceCode());
        return scriptId.map(uuid -> ScriptResponseDto
                .builder()
                .id(uuid)
                .location("/queue/" + uuid.toString())
                .build());
    }

    public Optional<ScriptExecutionItem> get(@NonNull UUID id) {
        // Get state of sourceCode from storage
        return db.get(id);
    }

    public ScriptResponseDto add(@NonNull String script) {
        return null;
    }

    /**
     * This class is responsible for checking the queue of scripts and further transfer for execution.
     */
    private class ScriptExecutionQueueHelperThread extends Thread {
        @Autowired
        JSExecutor scriptExecutor;

        /**
         * TODO: вынести переменные в конфигурационный файл
         */
        private final int TIME_TO_INTERRUPT = 20;
        private final TimeUnit TIME_SCALE = TimeUnit.SECONDS;
        private final int THREAD_POOL_SIZE = 4;

        private final ScheduledExecutorService pool = Executors.newScheduledThreadPool(THREAD_POOL_SIZE);

        @Override
        public void run() {
            while (true) {
                BlockingQueue<ScriptExecutionItem> scriptsQueue = db.getExecutionQueue();
                try {
                    //TODO: пересмотреть выброс исключения и дальнейшую обработку
                    ScriptExecutionItem item = scriptsQueue.take();
                    UUID id = item.getId();
                    Future executionResult = pool.submit(() -> scriptExecutor.execute(item));
                    pool.schedule(() -> {
                                if (executionResult.cancel(true)) {
                                    item.setStatus(ScriptExecutionItem.ExecutionStatus.INTERRUPTED);
                                }
                            },
                            TIME_TO_INTERRUPT,
                            TIME_SCALE
                    );
                    futureScriptExecutionResultMap.put(id, executionResult);
                } catch (RejectedExecutionException e) {
                    //TODO: обработать ситуацию, когда скрипт не может быть принят на выполнение executor-ом
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

