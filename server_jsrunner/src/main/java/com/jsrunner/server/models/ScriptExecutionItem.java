package com.jsrunner.server.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Builder;

import java.io.StringWriter;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScriptExecutionItem {


    private String sourceCode;
    private UUID id;
    private ScriptExecutionStatus status;

    private StringWriter writer;
    private StringWriter errorWriter;
    
    public ScriptExecutionItem(@NonNull String sourceCode, @NonNull UUID id, @NonNull ScriptExecutionStatus status) {
        this.sourceCode = sourceCode;
        this.id = id;
        this.status = status;
        writer = new StringWriter();
        errorWriter = new StringWriter();
    }

    @Override
    public String toString() {
        return "ScriptExecutionItem{" +
                "sourceCode='" + sourceCode + '\'' +
                ", id=" + id +
                '}';
    }
}