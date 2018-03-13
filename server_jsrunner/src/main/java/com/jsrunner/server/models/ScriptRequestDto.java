package com.jsrunner.server.models;

import lombok.Data;

/**
 * This class represents the request object, that contains mode of sourceCode execution and sourceCode source code
 */
@Data
public class ScriptRequestDto {
    ScriptRequestMode mode;
    String sourceCode;
}
