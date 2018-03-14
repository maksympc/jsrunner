package com.jsrunner.server.repository;

import com.jsrunner.server.models.ScriptExecutionItem;
import com.jsrunner.server.models.ScriptExecutionStatus;
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
    private static final int INITIAL_QUEUE_CAPACITY = 10000;
    private final BlockingQueue<ScriptExecutionItem> executionQueue;
    private final ConcurrentSkipListMap<UUID, ScriptExecutionItem> executionResultMap;

    //TODO: пересмотреть необходимость блокировки на запись и чтение
    private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private final Lock r = rwl.readLock();
    private final Lock w = rwl.writeLock();

    public MemoryStorage() {
        executionQueue = new LinkedBlockingQueue<>(INITIAL_QUEUE_CAPACITY);
        executionResultMap = new ConcurrentSkipListMap<>();
    }

    // TODO: пересмотреть случай, когда возвращается null!
    public Optional<UUID> add(String script) {
        w.lock();
        try {
            UUID id = UUID.randomUUID();
            ScriptExecutionItem scriptContainer = new ScriptExecutionItem(script, id, ScriptExecutionStatus.NEW);
            executionResultMap.put(id, scriptContainer);
            if (executionQueue.offer(scriptContainer)) {
                scriptContainer.setStatus(ScriptExecutionStatus.QUEUED);
            } else {
                scriptContainer.setStatus(ScriptExecutionStatus.REJECTED);
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