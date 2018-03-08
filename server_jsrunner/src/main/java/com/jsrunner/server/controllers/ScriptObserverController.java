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

    @DeleteMapping(value = "/queue/{uuid}")
    public ResponseEntity cancel(@PathVariable String uuid) {
        try {
            UUID id = UUID.fromString(uuid);
            log.info("Request with id={}", id);

            Optional<Boolean> isCanсelled = executorService.cancel(id);
            ResponseEntity response = buildCancelledResponseEntity(isCanсelled, id);

            log.info("Response for id={},response={}", id, response);
            return response;
        } catch (IllegalArgumentException e) {
            return buildIllegalArgumentExceptionResponseEntity(e, uuid);
        }
    }

    @GetMapping(value = "/queue/{uuid}")
    public ResponseEntity get(@PathVariable String uuid) {
        try {
            UUID id = UUID.fromString(uuid);
            log.info("Request with id={}", id);

            Optional<ScriptExecutionItem> scriptExecutionItem = executorService.get(id);
            ResponseEntity response = buildResponseEntity(scriptExecutionItem, id);

            log.info("Response for id={},response={}", id, response);
            return response;
        } catch (IllegalArgumentException e) {
            return buildIllegalArgumentExceptionResponseEntity(e, uuid);
        }
    }

    private ResponseEntity buildCancelledResponseEntity(Optional<Boolean> isCancelled, UUID id) {
        return isCancelled
                .map((cancelled) -> {
                    if (cancelled) {
                        return ResponseEntity
                                .status(HttpStatus.OK)
                                .body("Script with id={" + id + "} has been cancelled!");
                    } else {
                        return ResponseEntity
                                .status(HttpStatus.NO_CONTENT)
                                .body("Script with id={" + id + "} cannot be cancelled!");
                    }
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Element, with id:{" + id + "} not found!"));
    }

    private ResponseEntity buildResponseEntity(Optional<ScriptExecutionItem> scriptExecutionItem, UUID id) {
        Optional<ResponseEntity> response = scriptExecutionItem
                .map(item -> {
                    // check status and build response depend of script execution status
                    ScriptExecutionItem.ExecutionStatus status = item.getStatus();
                    if (status == ScriptExecutionItem.ExecutionStatus.NEW ||
                            status == ScriptExecutionItem.ExecutionStatus.QUEUED ||
                            status == ScriptExecutionItem.ExecutionStatus.RUNNING) {
                        return ResponseEntity
                                .status(HttpStatus.OK)
                                .body(ScriptExecutionItemResponseDto
                                        .builder()
                                        .id(item.getId())
                                        .source(item.getSourceCode())
                                        .status(status)
                                        .executionOutput(item.getWriter().toString() + item.getErrorWriter().toString())
                                        .cancelLink(ScriptExecutionItemResponseDto.Link.builder()
                                                .href("/queue/" + id)
                                                .method(HttpMethod.DELETE)
                                                .build())
                                        .build()
                                );
                    } else {
                        return ResponseEntity
                                .status(HttpStatus.SEE_OTHER)
                                .body(ScriptResponseDto
                                        .builder()
                                        .id(id)
                                        .location("/storage/" + id)
                                        .build());
                    }
                });

        return response.orElseGet(
                () -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Element, with id:{" + id + "} not found!"));
    }

    private ResponseEntity buildIllegalArgumentExceptionResponseEntity(IllegalArgumentException e, String uuid) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Error in link formation. The .../queue/{" + uuid + "} element must have UUID formatting. " + e.getMessage());
    }
}
