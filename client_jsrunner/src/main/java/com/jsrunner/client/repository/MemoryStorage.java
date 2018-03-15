package com.jsrunner.client.repository;

import com.jsrunner.client.models.ScriptExecutionItem;
import com.jsrunner.client.models.ScriptExecutionItemResponseDto;
import com.jsrunner.client.models.ScriptExecutionStatus;
import com.jsrunner.client.models.ScriptRequestDto;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.LinkedBlockingQueue;


//TODO: javadoc, обработать исключительные ситуации
//TODO: проставить по всем сервисам проверки на null, на входящие значения!
@Repository
public class MemoryStorage {

    private static final int INITIAL_QUEUE_CAPACITY = 100;
    private final ConcurrentSkipListMap<UUID, ScriptExecutionItemResponseDto> executionResultsMap;
    private final BlockingQueue<ScriptExecutionItem> requestsQueue;

    public MemoryStorage() {
        executionResultsMap = new ConcurrentSkipListMap<>();
        requestsQueue = new LinkedBlockingQueue<>(INITIAL_QUEUE_CAPACITY);
    }

    public UUID add(@NonNull ScriptRequestDto script) {
        UUID id = UUID.randomUUID();
        ScriptExecutionItem request = new ScriptExecutionItem(id, script);
        ScriptExecutionItemResponseDto response = ScriptExecutionItemResponseDto
                .builder()
                .id(id)
                .status(ScriptExecutionStatus.NEW)
                .build();
        executionResultsMap.put(id, response);

        if (!requestsQueue.offer(request)) {
            response.setStatus(ScriptExecutionStatus.REJECTED);
        }
        return id;
    }

    public Optional<ScriptExecutionItemResponseDto> get(@NonNull UUID id) {
        return Optional.ofNullable(executionResultsMap.get(id));
    }

    public boolean set(@NonNull UUID id, @NonNull ScriptExecutionItemResponseDto item) {
        if (executionResultsMap.get(id) == null) {
            return false;
        }
        executionResultsMap.put(id, item);
        return true;
    }

    public BlockingQueue<ScriptExecutionItem> getRequestsQueue() {
        return requestsQueue;
    }
}
