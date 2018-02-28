package com.jsrunner.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JSExecutionResultHttpResponseDto {
    private String executionResult;
    private HttpStatus httpStatusCode;
}
