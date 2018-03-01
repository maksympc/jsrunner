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
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
public class JSExecutorServiceTest {
    @Mock
    private ScheduledExecutorService scheduledExecutorService;
    @Mock
    private Future<JSExecutionResultDto> scriptTask;
    @InjectMocks
    private JSExecutorService service;

    @Test
    public void execute() throws ScriptException, ExecutionException, InterruptedException {
        //prepare
        String expectedExecutionResult = "Hello world";
        HttpStatus expectedStatusCode = HttpStatus.OK;

        when(scriptTask.get()).thenReturn(
                JSExecutionResultDto
                        .builder()
                        .executionResult(expectedExecutionResult)
                        .errors("")
                        .build()
        );
        when(scheduledExecutorService.submit(any(Callable.class))).thenReturn(scriptTask);

        //testing
        JSExecutionResultHttpResponseDto response = service.execute("print(\"Hello world\");");
        //validate
        assertThat(response.getExecutionResult(), is(expectedExecutionResult));
        assertThat(response.getHttpStatusCode(), is(expectedStatusCode));
    }

    @Test
    public void executeWithInnerScriptException() throws ScriptException, ExecutionException, InterruptedException {
        //prepare
        String expectedExecutionResult = "Error message";
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        when(scriptTask.get()).thenReturn(
                JSExecutionResultDto
                        .builder()
                        .executionResult("")
                        .errors(expectedExecutionResult)
                        .build()
        );
        when(scheduledExecutorService.submit(any(Callable.class))).thenReturn(scriptTask);
        //testing
        JSExecutionResultHttpResponseDto response = service.execute("throw new Error(\"Error message\");");
        //validate
        assertThat(response.getExecutionResult(), is(expectedExecutionResult));
        assertThat(response.getHttpStatusCode(), is(expectedStatusCode));
    }
}