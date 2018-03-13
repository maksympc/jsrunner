package com.jsrunner.server.controllers;


import com.jsrunner.server.models.ScriptRequestDto;
import com.jsrunner.server.models.ScriptRequestMode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {

    @RequestMapping("/test")
    public ResponseEntity<ScriptRequestDto> testScriptDto() {
        ScriptRequestDto dto = new ScriptRequestDto();
        dto.setSourceCode("SourceCode");
        dto.setMode(ScriptRequestMode.ASYNC);
        return ResponseEntity.ok(dto);
    }

}
