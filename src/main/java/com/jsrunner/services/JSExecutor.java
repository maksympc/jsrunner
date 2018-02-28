package com.jsrunner.services;

import com.jsrunner.model.JSExecutionResultDto;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.StringWriter;

@Service
public class JSExecutor {

    private String engineName = "nashorn";
    private ScriptEngine engine;
    private StringWriter writer;
    private StringWriter errorWriter;

    public JSExecutor() {
        engine = new ScriptEngineManager().getEngineByName(engineName);
        writer = new StringWriter();
        errorWriter = new StringWriter();
    }

    public JSExecutionResultDto execute(@NonNull String script) throws ScriptException {
        ScriptContext context = engine.getContext();
        context.setWriter(writer);
        context.setErrorWriter(errorWriter);
        engine.eval(script);
        return buildExecutionResultDto(writer, errorWriter);
    }

    private JSExecutionResultDto buildExecutionResultDto(@NonNull StringWriter writer, @NonNull StringWriter errorWriter) {
        return JSExecutionResultDto
                .builder()
                .executionResult(writer.toString())
                .errors(errorWriter.toString())
                .build();
    }

}
