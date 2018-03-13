package com.jsrunner.server.services;


import com.jsrunner.server.models.ScriptExecutionItem;
import com.jsrunner.server.models.ScriptExecutionItemResponseDto;
import com.jsrunner.server.models.ScriptResponseDto;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ScriptUtilsService {

    /**
     * RECEIVER CONTROLLER METHODS
     */
    public ResponseEntity buildServerUnavailableResponse(Optional<ScriptResponseDto> executionResult) {
        return executionResult
                .map((item) -> ResponseEntity
                        .accepted()
                        .body(item))
                .orElse(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(ScriptResponseDto
                                .builder()
                                .errors("The server is currently unable to handle the request due to a temporary overloading or maintenance of the server. Please, try again later!")
                                .build()));
    }

    /**
     * OBSERVER CONTROLLER METHODS
     * TODO: rename methods
     */
    public ResponseEntity buildCancelledResponse(Optional<Boolean> isCancelled, UUID id) {
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

    public ResponseEntity buildResponseEntity(Optional<ScriptExecutionItem> scriptExecutionItem, UUID id) {
        Optional<ResponseEntity> response = scriptExecutionItem
                .map(item -> {
                    // check status and build executionResult depend of script execution status
                    ScriptExecutionItem.ExecutionStatus status = item.getStatus();
                    if (status == ScriptExecutionItem.ExecutionStatus.NEW ||
                            status == ScriptExecutionItem.ExecutionStatus.QUEUED ||
                            status == ScriptExecutionItem.ExecutionStatus.RUNNING) {
                        return ResponseEntity
                                .status(HttpStatus.OK)
                                .body(ScriptExecutionItemResponseDto
                                        .builder()
                                        .id(item.getId())
                                        .status(status)
                                        .output(item.getWriter().toString() + item.getErrorWriter().toString())
                                        .cancelExecutionLink(ScriptExecutionItemResponseDto.Link.builder()
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

    public ResponseEntity buildIllegalArgumentResponse(IllegalArgumentException e, String uuid) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Error in link formation. The .../queue/{" + uuid + "} element must have UUID formatting. " + e.getMessage());
    }
}
