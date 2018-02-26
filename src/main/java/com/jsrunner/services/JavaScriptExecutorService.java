package com.jsrunner.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * This class contains the logic for handling JavaScript, correct processing and returning the result of the
 * script execution.
 * <p>
 * <b>Under development<b/>
 *
 * @version 0.1
 */
@Service
@PropertySource("classpath:application.properties")
public class JavaScriptExecutorService {

    private String engineName = "nashorn";
    private ScriptEngine engine = new ScriptEngineManager().getEngineByName(engineName);

    public void execute(String script) throws FileNotFoundException, ScriptException {
        engine.eval(script);
    }
    
}
