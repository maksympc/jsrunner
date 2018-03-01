package com.jsrunner.services;

import com.jsrunner.model.JSExecutionResultDto;
import org.junit.Test;

import javax.script.ScriptException;

import java.util.Arrays;

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

    @Test
    public void executeBadFormattedScript() throws ScriptException {
        //prepare
        String expectedExecutionResult = "";
        String[] expectedErrors = ("javax.script.ScriptException: <eval>:1:5 Expected ; but found t\r\n" +
                "prin t(\"Hello world!\")\r\n" +
                "     ^ in <eval> at line number 1 at column number 5").split("\\s");

        executor = new JSExecutor();
        //testing
        JSExecutionResultDto result = executor.execute("prin t(\"Hello world!\")");
        //validate
        assertArrayEquals(expectedErrors, result.getErrors().split("\\s"));
        assertThat(result.getExecutionResult(), is(expectedExecutionResult));
    }

}