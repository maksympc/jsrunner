package com.jsrunner.client.models;

import lombok.Data;

@Data
public class JsRunnerResponseDto {
    private String id;
    private String location;
    private String errors;

    @Override
    public String toString() {
        return "JsRunnerResponseDto{" +
                "id='" + id + '\'' +
                ", location='" + location + '\'' +
                ", errors='" + errors + '\'' +
                '}';
    }
}
