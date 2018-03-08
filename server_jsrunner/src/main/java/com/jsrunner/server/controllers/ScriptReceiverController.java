package com.jsrunner.server.controllers;

import com.jsrunner.server.model.ScriptResponseDto;
import com.jsrunner.server.model.ScriptRequestDto;
import com.jsrunner.server.services.ScriptExecutorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * This class is used to receive requests for ("/js") mapping. It's an entry point of API to add JavaScript's scripts
 *
 * @version 0.2
 */
@Slf4j
@RestController
@RequestMapping("/js")
public class ScriptReceiverController {

    @Autowired
    private ScriptExecutorService scriptExecutorService;

    @GetMapping
    public ResponseEntity<String> termsOfUse() {
        String termsOfUse = "To add JavaScript sourceCode you should pass it in the body of request with next configuration<br>" +
                "1) RequestMethod : POST <br>" +
                "2) Content-Type : application/json. <br>Example:<br>{\"mode\":\"ASYNC\",\"scriptSource\":\"print(\"Hello world!\");\"}";
        return ResponseEntity.accepted().header("Content-Type", "text/html").body(termsOfUse);
    }

    @PostMapping
    public ResponseEntity<ScriptResponseDto> execute(@RequestBody ScriptRequestDto request) {
        log.info("Request body:\n{}", request);

        Optional<ScriptResponseDto> scriptResponseDto = scriptExecutorService.add(request);
        ResponseEntity response = buildResponseEntity(scriptResponseDto);

        log.info("Response body:\n{}", response);
        return response;
    }

    private ResponseEntity buildResponseEntity(Optional<ScriptResponseDto> executionResult) {
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
}
