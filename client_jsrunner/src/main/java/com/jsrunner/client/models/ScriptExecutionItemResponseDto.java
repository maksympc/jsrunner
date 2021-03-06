package com.jsrunner.client.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScriptExecutionItemResponseDto {
    private UUID id;
    private ScriptExecutionStatus status;
    private String output;
    private String errors;
}
