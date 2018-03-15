package com.jsrunner.client.controllers;

import com.jsrunner.client.models.ScriptExecutionItemResponseDto;
import com.jsrunner.client.models.ScriptExecutionStatus;
import com.jsrunner.client.models.ScriptRequestDto;
import com.jsrunner.client.models.DefaultResponseDto;
import com.jsrunner.client.services.AsyncJsRunnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Controller
@Slf4j
public class ScriptDispatcherController {

    //    TODO : рефакторинг (строковые константы, обработка ошибок, документация)
    @Autowired
    private AsyncJsRunnerService service;

    @PostMapping("/js")
    public ResponseEntity sendScript(@Valid @RequestBody ScriptRequestDto request) {
        log.info("/js request={}", request);
        ResponseEntity response;

        switch (request.getMode()) {
            case "ASYNC":
                ScriptExecutionItemResponseDto responseItem = service.add(request);
                response = ResponseEntity.ok().body(responseItem);
                break;
            case "SYNC":
                DefaultResponseDto defaultResponse = DefaultResponseDto
                        .builder()
                        .rawResponse("SYNC mode is not supported now!")
                        .build();
                response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(defaultResponse);
                break;
            default:
                response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unsupported mode format={" + request.getMode() + "}, supported only SYNC and ASYNC");
                break;
        }

        log.info("/js response={}", response);
        return response;
    }

    //TODO : обработать IllegalArgumentException
    @GetMapping("/storage/{uuid}")
    public ResponseEntity<ScriptExecutionItemResponseDto> getExecutionResult(@Valid @PathVariable String uuid) {
        log.info("/storage with id={}", uuid);
        ScriptExecutionItemResponseDto responseItem = ScriptExecutionItemResponseDto
                .builder()
                .id(new UUID(0, 0))
                .status(ScriptExecutionStatus.REJECTED)
                .output("")
                .errors("")
                .build();
        try {
            UUID id = UUID.fromString(uuid);
            responseItem = service.get(id);
            return ResponseEntity.ok(responseItem);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseItem);
    }

    @GetMapping("/")
    public String getIndex() {
        return "index";
    }

    @GetMapping("/test")
    public String getTest() {
        return "test";
    }

}
