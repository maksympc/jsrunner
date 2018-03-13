package com.jsrunner.server.controllers;

import com.jsrunner.server.models.ScriptExecutionItem;
import com.jsrunner.server.services.ScriptExecutorService;
import com.jsrunner.server.services.ScriptUtilsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@Slf4j
public class ScriptObserverController {

    @Autowired
    private ScriptExecutorService executorService;
    @Autowired
    private ScriptUtilsService utils;

    @DeleteMapping(value = "/queue/{uuid}")
    public ResponseEntity cancel(@PathVariable String uuid) {
        try {
            UUID id = UUID.fromString(uuid);
            log.info("Request with id={}", id);

            Optional<Boolean> isCanсelled = executorService.cancel(id);
            ResponseEntity response = utils.buildCancelledResponse(isCanсelled, id);

            log.info("Response for id={},executionResult={}", id, response);
            return response;
        } catch (IllegalArgumentException e) {
            return utils.buildIllegalArgumentResponse(e, uuid);
        }
    }

    @GetMapping(value = "/queue/{uuid}")
    public ResponseEntity get(@PathVariable String uuid) {
        try {
            UUID id = UUID.fromString(uuid);
            log.info("Request with id={}", id);

            Optional<ScriptExecutionItem> scriptExecutionItem = executorService.get(id);
            ResponseEntity response = utils.buildResponseEntity(scriptExecutionItem, id);

            log.info("Response for id={},executionResult={}", id, response);
            return response;
        } catch (IllegalArgumentException e) {
            //TODO: передавать относительный путь, как строку
            return utils.buildIllegalArgumentResponse(e, uuid);
        }
    }

}
