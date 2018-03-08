package com.jsrunner.server.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScriptExecutionItemResponseDto {
    private UUID id;
    private ScriptExecutionItem.ExecutionStatus status;
    private String source;
    private String executionOutput;
    private Link cancelLink;

    @Data
    @AllArgsConstructor
    @Builder
    public static class Link {
        private HttpMethod method;
        private String href;
    }
}
