package com.jsrunner.client.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;


/**
 * Component, that contains information about script execution process
 */
@Data
@AllArgsConstructor
public class ScriptExecutionItem {
    private UUID id;
    private ScriptRequestDto script;
}
