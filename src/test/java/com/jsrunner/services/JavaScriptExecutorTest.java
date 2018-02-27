package com.jsrunner.services;

import org.junit.Test;

import javax.script.ScriptException;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class JavaScriptExecutorTest {

    private JavaScriptExecutor executor;

    @Test
    public void execute() throws ScriptException {
        //prepare
        String expected = "Hello world!\r\n";
        executor = new JavaScriptExecutor();
        //testing
        String result = executor.execute("print(\"Hello world!\")");
        //validate
        assertThat(result, is(expected));
    }
}