package com.jsrunner.server.controllers;


import com.jsrunner.server.model.ScriptExecutionItem;
import com.jsrunner.server.model.ScriptExecutionItemResponseDto;
import com.jsrunner.server.model.ScriptResponseDto;
import com.jsrunner.server.services.ScriptExecutorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;


/**
 * This controller providing access to already executed or terminated scripts
 */
@RequestMapping("/storage")
@RestController
@Slf4j
public class ScriptStorageController {

    @Autowired
    ScriptExecutorService executorService;

    @GetMapping("/{uuid}")
    public ResponseEntity get(@PathVariable String uuid) {
        try {
            UUID id = UUID.fromString(uuid);
            log.info("Request with id={}", id);

            Optional<ScriptExecutionItem> scriptExecutionItem = executorService.get(id);
            ResponseEntity response = buildResponseEntity(scriptExecutionItem, id);

            log.info("Response for id={},executionResult={}", id, response);
            return response;
        } catch (IllegalArgumentException e) {
            return buildIllegalArgumentExceptionResponseEntity(e, uuid);
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
                                    .source(item.getSourceCode())
                                    .status(status)
                                    .executionOutput(item.getWriter().toString() + item.getErrorWriter().toString())
                                    .build()
                            );
                });

        return response.orElseGet(
                () -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Element, with id:{" + id + "} not found!"));
    }

    private ResponseEntity buildIllegalArgumentExceptionResponseEntity(IllegalArgumentException e, String uuid) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error in link formation. The .../queue/{" + uuid + "} element must have UUID formatting. " + e.getMessage());
    }
}
