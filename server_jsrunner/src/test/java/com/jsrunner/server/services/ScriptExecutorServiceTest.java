package com.jsrunner.server.services;

import com.jsrunner.server.model.ScriptExecutionItem;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;


@RunWith(SpringRunner.class)
public class ScriptExecutorServiceTest {
    @Mock
    private ScheduledExecutorService scheduledExecutorService;
    @Mock
    private Future<ScriptExecutionItem> scriptTask;
    @InjectMocks
    private ScriptExecutorService service;

//    @Test
//    public void execute() throws ScriptException, ExecutionException, InterruptedException {
//        //prepare
//        String expectedExecutionResult = "Hello world";
//        HttpStatus expectedStatusCode = HttpStatus.OK;
//
//        when(scriptTask.get()).thenReturn(
//                ScriptExecutionItem
//                        .builder()
//                        .executionResult(expectedExecutionResult)
//                        .errors("")
//                        .build()
//        );
//        when(scheduledExecutorService.submit(any(Callable.class))).thenReturn(scriptTask);
//
//        //testing
//        ScriptResponseDto response = service.add("print(\"Hello world\");");
//        //validate
//        assertThat(response.getExecutionResult(), is(expectedExecutionResult));
//        assertThat(response.getHttpStatusCode(), is(expectedStatusCode));
//    }
//
//    @Test
//    public void executeWithInnerScriptException() throws ScriptException, ExecutionException, InterruptedException {
//        //prepare
//        String expectedExecutionResult = "Error message";
//        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
//        when(scriptTask.get()).thenReturn(
//                ScriptExecutionItem
//                        .builder()
//                        .executionResult("")
//                        .errors(expectedExecutionResult)
//                        .build()
//        );
//        when(scheduledExecutorService.submit(any(Callable.class))).thenReturn(scriptTask);
//        //testing
//        ScriptResponseDto response = service.add("throw new Error(\"Error message\");");
//        //validate
//        assertThat(response.getExecutionResult(), is(expectedExecutionResult));
//        assertThat(response.getHttpStatusCode(), is(expectedStatusCode));
//    }
}