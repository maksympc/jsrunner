package com.jsrunner.client.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class ScriptExecutionItemResponseDto {
    private UUID id;
    private ScriptExecutionStatus status;
    private String output;
}
