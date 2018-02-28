package com.jsrunner.services;

import com.jsrunner.model.JSExecutionResultDto;
import com.jsrunner.model.JSExecutionResultHttpResponseDto;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.script.ScriptException;

/**
 * This class contains the logic for handling JavaScript, correct processing and returning the result of the
 * script execution.
 *
 * @version 0.1
 */
@Service
public class JSExecutorService {

    @Autowired
    JSExecutor executor;

    public JSExecutionResultHttpResponseDto execute(@NonNull String script) {
        JSExecutionResultHttpResponseDto response = new JSExecutionResultHttpResponseDto();
        try {
            JSExecutionResultDto executionResult = executor.execute(script);
            return buildJSJsExecutionResultHttpResponseDto(executionResult);
        } catch (ScriptException e) {
            return buildJSJsExecutionResultHttpResponseDto(e);
        }
    }

    private JSExecutionResultHttpResponseDto buildJSJsExecutionResultHttpResponseDto(Exception e) {
        return JSExecutionResultHttpResponseDto
                .builder()
                .executionResult(e.getMessage())
                .httpStatusCode(HttpStatus.BAD_REQUEST)
                .build();
    }

    private JSExecutionResultHttpResponseDto buildJSJsExecutionResultHttpResponseDto(JSExecutionResultDto dto) {
        StringBuilder sb = new StringBuilder();
        sb.append(dto.getExecutionResult());
        sb.append(dto.getErrors());
        HttpStatus statusCode = HttpStatus.OK;
        if (dto.getErrors().length() != 0) {
            statusCode = HttpStatus.BAD_REQUEST;
        }
        return JSExecutionResultHttpResponseDto
                .builder()
                .executionResult(sb.toString())
                .httpStatusCode(statusCode)
                .build();
    }
}
