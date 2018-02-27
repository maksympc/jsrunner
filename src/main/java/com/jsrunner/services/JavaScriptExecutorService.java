package com.jsrunner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;

/**
 * This class contains the logic for handling JavaScript, correct processing and returning the result of the
 * script execution.
 *
 * @version 0.1
 */
@Service
public class JavaScriptExecutorService {

    @Autowired
    @Qualifier("jsexecutor")
    JavaScriptExecutor executor;

    public String execute(String script) {
        try {
            return executor.execute(script);
        } catch (ScriptException e) {
            return e.getMessage();
        }
    }
}
