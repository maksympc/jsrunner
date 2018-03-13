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

    /**
     * Contains the status of the sourceCode
     */
    public enum ExecutionStatus {
        /**
         * A sourceCode that has this status was just created
         */
        NEW,
        /**
         * A sourceCode that has this status can not be added to the execution queue.
         */
        REJECTED,
        /**
         * A sourceCode that has this status is added to the execution queue.
         */
        QUEUED,
        /**
         * The sourceCode that has this status has been aborted by a client.
         */
        CANCELLED,
        /**
         * The sourceCode that has this status is running now.
         */
        RUNNING,
        /**
         * The sourceCode that has this status was interrupted, exceeding the execution time.
         */
        INTERRUPTED,
        /**
         * The sourceCode that has this status was successfully executed.
         */
        COMPLETED_SUCCESSFULLY,
        /**
         * The sourceCode that has this status is completed with errors.
         */
        COMPLETED_WITH_ERRORS,
    }

    private String sourceCode;
    private UUID id;
    private ExecutionStatus status;

    private StringWriter writer;
    private StringWriter errorWriter;
    
    public ScriptExecutionItem(@NonNull String sourceCode, @NonNull UUID id, @NonNull ExecutionStatus status) {
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