package com.jsrunner.server.models;

/**
 * Contains the status of the script item
 */
public enum ScriptExecutionStatus {
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
