package com.jsrunner.services;

import com.jsrunner.model.JSExecutionResultDto;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.script.*;
import java.io.StringWriter;
import java.io.Writer;

@Slf4j
@Service
public class JSExecutor {

    private String engineName = "nashorn";
    private ScriptEngine engine;

    public JSExecutor() {
        engine = new ScriptEngineManager().getEngineByName(engineName);
    }

    public JSExecutionResultDto execute(@NonNull String script) {
        ScriptContext context = new SimpleScriptContext();
        context.setBindings(engine.createBindings(), ScriptContext.ENGINE_SCOPE);
        context.setWriter(new StringWriter());
        context.setErrorWriter(new StringWriter());
        try {
            engine.eval(script, context);
            return buildExecutionResultDto(context.getWriter(), context.getErrorWriter());
        } catch (ScriptException e) {
            log.info("An exception has been occurred, while running the script\n" +
                    "Script:{}" +
                    "\nException:{}", script, e.getLocalizedMessage());
            return buildExecutionResultDto(context.getWriter(), context.getErrorWriter(), e);
        }
    }

    private JSExecutionResultDto buildExecutionResultDto(@NonNull Writer writer, @NonNull Writer errorWriter) {
        return JSExecutionResultDto
                .builder()
                .executionResult(writer.toString())
                .errors(errorWriter.toString())
                .build();
    }

    private JSExecutionResultDto buildExecutionResultDto(@NonNull Writer writer, @NonNull Writer errorWriter, @NonNull ScriptException e) {
        JSExecutionResultDto result = buildExecutionResultDto(writer, errorWriter);
        result.setErrors(result.getErrors() + e);
        return result;
    }

}
