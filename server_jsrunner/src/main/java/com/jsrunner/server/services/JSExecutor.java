package com.jsrunner.server.services;

import com.jsrunner.server.model.ScriptExecutionItem;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.script.*;

@Slf4j
@Service
public class JSExecutor {

    //TODO: перенести константу в application.properties
    private String engineName = "nashorn";
    private ScriptEngine engine;

    public JSExecutor() {
        engine = new ScriptEngineManager().getEngineByName(engineName);
    }

    public void execute(@NonNull ScriptExecutionItem scriptItem) {
        log.info("Start executing: " + scriptItem);
        // step 0. Check script status
        if (scriptItem.getStatus() == ScriptExecutionItem.ExecutionStatus.CANCELLED) {
            return;
        }
        // step 1. Create new context for each scriptItem
        ScriptContext context = new SimpleScriptContext();
        context.setBindings(engine.createBindings(), ScriptContext.ENGINE_SCOPE);
        // step 2. Change script status to RUNNING
        scriptItem.setStatus(ScriptExecutionItem.ExecutionStatus.RUNNING);
        // step 3. Redirect script output
        context.setWriter(scriptItem.getWriter());
        context.setErrorWriter(scriptItem.getErrorWriter());
        try {
            // step 4. Execute script
            engine.eval(scriptItem.getSourceCode(), context);
            // step 5.0 Change script status to COMPLETED_SUCCESSFULLY
            scriptItem.setStatus(ScriptExecutionItem.ExecutionStatus.COMPLETED_SUCCESSFULLY);
        } catch (ScriptException e) {
            log.info("An exception has been occurred, while running the script\n" +
                    "Script:{}\nException:{}", scriptItem, e.getLocalizedMessage());
            // step 5.1.0 If exception has been occurred, add exception message to scriptItem's errorWriter
            scriptItem.getErrorWriter().write(" ScriptException: " + e.getLocalizedMessage());
            // step 5.1.1 Change script status to COMPLETED_WITH_ERRORS
            scriptItem.setStatus(ScriptExecutionItem.ExecutionStatus.COMPLETED_WITH_ERRORS);
        } finally {
            log.info("End processing: " + scriptItem);
        }
    }
}
