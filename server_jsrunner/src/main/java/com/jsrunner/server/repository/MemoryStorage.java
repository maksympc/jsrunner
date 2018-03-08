package com.jsrunner.server.repository;

import com.jsrunner.server.model.ScriptExecutionItem;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Repository
public class MemoryStorage {
    private static final int INITIAL_QUEUE_CAPACITY = 100;
    private final BlockingQueue<ScriptExecutionItem> executionQueue;
    private final ConcurrentSkipListMap<UUID, ScriptExecutionItem> executionResultMap;

    //    private static final int TIMEOUT_TO_ADD = 1;
//    private static final TimeUnit TIMEUNIT_TO_ADD = TimeUnit.SECONDS;

    //TODO: пересмотреть необходимость блокировки на запись и чтение
    private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private final Lock r = rwl.readLock();
    private final Lock w = rwl.writeLock();

    public MemoryStorage() {
        executionQueue = new LinkedBlockingQueue<>(INITIAL_QUEUE_CAPACITY);
        executionResultMap = new ConcurrentSkipListMap<>();
    }

    public Optional<UUID> add(String script) {
        w.lock();
        try {
            UUID id = UUID.randomUUID();
            ScriptExecutionItem scriptContainer = new ScriptExecutionItem(script, id, ScriptExecutionItem.ExecutionStatus.NEW);
            executionResultMap.put(id, scriptContainer);
            if (executionQueue.offer(scriptContainer)) {
                scriptContainer.setStatus(ScriptExecutionItem.ExecutionStatus.QUEUED);
            } else {
                scriptContainer.setStatus(ScriptExecutionItem.ExecutionStatus.REJECTED);
                return Optional.ofNullable(null);
            }
            return Optional.of(id);
        } finally {
            w.unlock();
        }
    }

    public Optional<ScriptExecutionItem> get(UUID id) {
        r.lock();
        try {
            return Optional.ofNullable(executionResultMap.get(id));
        } finally {
            r.unlock();
        }
    }

    public BlockingQueue<ScriptExecutionItem> getExecutionQueue() {
        return executionQueue;
    }
}