package com.jsrunner.client.models;

/**
 * Contains the status of the script item
 */
public enum ScriptExecutionStatus {
    /**
     * A script that has this status has been created and sending to remote host
     */
    SENDING,
    /**
     * A script that has this status was just created
     */
    NEW,
    /**
     * A script that has this status can not be added to the execution queue.
     */
    REJECTED,
    /**
     * A script that has this status is added to the execution queue.
     */
    QUEUED,
    /**
     * The script that has this status has been aborted by a client.
     */
    CANCELLED,
    /**
     * The script that has this status is running now.
     */
    RUNNING,
    /**
     * The script that has this status was interrupted, exceeding the execution time.
     */
    INTERRUPTED,
    /**
     * The script that has this status was successfully executed.
     */
    COMPLETED_SUCCESSFULLY,
    /**
     * The script that has this status is completed with errors.
     */
    COMPLETED_WITH_ERRORS,
}
