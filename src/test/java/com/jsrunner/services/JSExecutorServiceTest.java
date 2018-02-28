package com.jsrunner.services;

import com.jsrunner.model.JSExecutionResultDto;
import com.jsrunner.model.JSExecutionResultHttpResponseDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import javax.script.ScriptException;
import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
public class JSExecutorServiceTest {

    @Mock
    private JSExecutor executor;
    @InjectMocks
    private JSExecutorService service;

    @Test
    public void execute() throws ScriptException {
        //prepare
        String expectedExecutionResult = "Hello world";
        HttpStatus expectedStatusCode = HttpStatus.OK;
        when(executor.execute(any())).thenReturn(
                JSExecutionResultDto
                        .builder()
                        .executionResult("Hello world")
                        .errors("")
                        .build());
        //testing
        JSExecutionResultHttpResponseDto response = service.execute("print(\"Hello world\");");
        //validate
        assertThat(response.getExecutionResult(), is(expectedExecutionResult));
        assertThat(response.getHttpStatusCode(), is(expectedStatusCode));
    }

    @Test
    public void executeWithInnerScriptException() throws ScriptException {
        //prepare
        String expectedExecutionResult = "Error message";
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        when(executor.execute(any())).thenThrow(new ScriptException("Error message"));
        //testing
        JSExecutionResultHttpResponseDto response = service.execute("throw new Error(\"Error message\");");
        //validate
        assertThat(response.getExecutionResult(), is(expectedExecutionResult));
        assertThat(response.getHttpStatusCode(), is(expectedStatusCode));
    }
}