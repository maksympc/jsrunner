package com.jsrunner.services;

import com.jsrunner.model.JSExecutionResultDto;
import org.junit.Test;

import javax.script.ScriptException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class JSExecutorTest {

    JSExecutor executor;

    @Test
    public void execute() throws ScriptException {
        //prepare
        String expectedExecutionResult = "Hello world!\r\n";
        String expectedErrors = "";
        executor = new JSExecutor();
        //testing
        JSExecutionResultDto result = executor.execute("print(\"Hello world!\")");
        //validate
        assertThat(result.getErrors(), is(expectedErrors));
        assertThat(result.getExecutionResult(), is(expectedExecutionResult));
    }

    @Test(expected = ScriptException.class)
    public void executeBadFormattedScript() throws ScriptException {
        //prepare
        executor = new JSExecutor();
        //testing
        executor.execute("prin t(\"Hello world!\")");
    }

}