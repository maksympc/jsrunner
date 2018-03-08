package com.jsrunner.server.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScriptResponseDto {
    private UUID id;
    private String location;
    private String errors;
}
