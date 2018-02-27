package com.jsrunner.services;

import org.springframework.stereotype.Component;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.StringWriter;

@Component("jsexecutor")
public class JavaScriptExecutor {

    private String engineName;
    private ScriptEngine engine;

    public JavaScriptExecutor() {
        engineName = "nashorn";
        engine = new ScriptEngineManager().getEngineByName(engineName);
    }

    public String execute(String script) throws ScriptException {
        StringWriter sw = new StringWriter();
        ScriptContext context = engine.getContext();
        context.setWriter(sw);
        context.setErrorWriter(sw);
        engine.eval(script);
        return sw.toString();
    }

}
