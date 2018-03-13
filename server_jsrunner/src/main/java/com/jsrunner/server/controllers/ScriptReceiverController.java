package com.jsrunner.server.controllers;

import com.jsrunner.server.models.ScriptResponseDto;
import com.jsrunner.server.models.ScriptRequestDto;
import com.jsrunner.server.services.ScriptExecutorService;
import com.jsrunner.server.services.ScriptUtilsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * This class is used to receive requests for ("/js") mapping. It's an entry point of API to add JavaScript's scripts
 *
 * @version 0.2
 */
@Slf4j
@Controller
@RequestMapping("/js")
public class ScriptReceiverController {

    @Autowired
    private ScriptExecutorService scriptExecutorService;
    @Autowired
    private ScriptUtilsService utils;

    @GetMapping
    public String getIndex() {
        return "index";
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<ScriptResponseDto> execute(@RequestBody ScriptRequestDto request) {
        log.info("Request body:\n{}", request);

        Optional<ScriptResponseDto> scriptResponseDto = scriptExecutorService.add(request);
        ResponseEntity response = utils.buildServerUnavailableResponse(scriptResponseDto);

        log.info("Response body:\n{}", response);
        return response;
    }


}
