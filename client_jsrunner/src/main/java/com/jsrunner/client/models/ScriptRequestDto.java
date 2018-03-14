package com.jsrunner.client.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ScriptRequestDto {
    String mode;
    String sourceCode;

    @Override
    public String toString() {
        return "ScriptRequestDto{" +
                "mode='" + mode + '\'' +
                ", sourceCode='" + sourceCode + '\'' +
                '}';
    }
}
