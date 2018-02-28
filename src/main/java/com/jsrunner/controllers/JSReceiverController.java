package com.jsrunner.controllers;

import com.jsrunner.model.JSExecutionResultHttpResponseDto;
import com.jsrunner.services.JSExecutorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class is used to receive requests for ("/js") mapping. It's an entry point of API to execute JavaScript's scripts
 *
 * @version 0.2
 */
@Slf4j
@RestController
@RequestMapping("/js")
public class JSReceiverController {

    @Autowired
    private JSExecutorService executorService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> termsOfUse() {
        String termsOfUse = "To execute JavaScript script you should pass it in the body of request with next configuration<br>" +
                "1) RequestMethod : POST <br>" +
                "2) Content-Type : application/javascript";
        return ResponseEntity.accepted().header("Content-Type", "text/html").body(termsOfUse);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> execute(@RequestBody String script) {
        log.info("Request body:\n{}", script);
        JSExecutionResultHttpResponseDto executionResult = executorService.execute(script);
        ResponseEntity response = buildResponseEntity(executionResult);
        log.info("Response body:\n{}", response);
        return response;
    }

    private ResponseEntity buildResponseEntity(JSExecutionResultHttpResponseDto executionResult) {
        ResponseEntity response = new ResponseEntity(executionResult.getExecutionResult(), executionResult.getHttpStatusCode());
        return response;
    }

}
