package com.jsrunner.client.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class JsRunnerRequestDto {
    String mode;
    String sourceCode;
}
