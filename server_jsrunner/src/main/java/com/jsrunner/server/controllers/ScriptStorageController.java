package com.jsrunner.server.controllers;


import com.jsrunner.server.models.ScriptExecutionItem;
import com.jsrunner.server.models.ScriptExecutionItemResponseDto;
import com.jsrunner.server.services.ScriptExecutorService;
import com.jsrunner.server.services.ScriptUtilsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;


/**
 * This controller providing access to already executed or terminated scripts
 */
// TODO: перенести логику в сервисы, в контроллерах оставить только вызовы методов и перенаправки процессинга
@RestController
@Slf4j
public class ScriptStorageController {

    @Autowired
    private ScriptExecutorService executorService;
    @Autowired
    private ScriptUtilsService utils;


    @GetMapping("/storage/{uuid}")
    public ResponseEntity get(@PathVariable String uuid) {
        try {
            UUID id = UUID.fromString(uuid);
            log.info("Request with id={}", id);

            Optional<ScriptExecutionItem> scriptExecutionItem = executorService.get(id);
            ResponseEntity response = buildResponseEntity(scriptExecutionItem, id);

            log.info("Response for id={},executionResult={}", id, response);
            return response;
        } catch (IllegalArgumentException e) {
            return utils.buildIllegalArgumentResponse(e, uuid);
        }
    }

    private ResponseEntity buildResponseEntity(Optional<ScriptExecutionItem> scriptExecutionItem, UUID id) {
        Optional<ResponseEntity> response = scriptExecutionItem
                .map(item -> {
                    // check status and build executionResult depend of script execution status
                    ScriptExecutionItem.ExecutionStatus status = item.getStatus();
                    return ResponseEntity
                            .status(HttpStatus.OK)
                            .body(ScriptExecutionItemResponseDto
                                    .builder()
                                    .id(item.getId())
                                    .status(status)
                                    .output(item.getWriter().toString() + item.getErrorWriter().toString())
                                    .build()
                            );
                });

        return response.orElseGet(
                () -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Element, with id:{" + id + "} not found!"));
    }
}
