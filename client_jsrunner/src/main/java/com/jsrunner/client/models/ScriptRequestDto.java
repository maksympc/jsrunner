package com.jsrunner.client.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScriptRequestDto {
    @NotBlank(message = "'mode' must not be blank!")
    String mode;
    @NotBlank(message = "'sourceCode' must not be blank!")
    String sourceCode;

    @Override
    public String toString() {
        return "ScriptRequestDto{" +
                "mode='" + mode + '\'' +
                ", sourceCode='" + sourceCode + '\'' +
                '}';
    }
}
