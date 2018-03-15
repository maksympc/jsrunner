package com.jsrunner.client.services;

import com.jsrunner.client.models.ScriptExecutionItemResponseDto;
import com.jsrunner.client.models.ScriptRequestDto;
import com.jsrunner.client.repository.MemoryStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


//TODO: переделать способ отправки,
//TODO: создать пулл из 4 потов для обработки очереди запросов
@Service
public class AsyncJsRunnerService {

    @Autowired
    private MemoryStorage db;
    @Autowired
    private AsyncJsRunnerClientApi api;

    /**
     * Add script to execution request queue
     */
    public ScriptExecutionItemResponseDto add(ScriptRequestDto script) {
        UUID id = db.add(script);
        return db.get(id).get();
    }

    public ScriptExecutionItemResponseDto get(UUID id) {
        return db.get(id).get();
    }

}
