package com.jsrunner.services;

import com.jsrunner.model.JSExecutionResultDto;
import com.jsrunner.model.JSExecutionResultHttpResponseDto;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

/**
 * This class contains the logic for handling JavaScript, correct processing and returning the result of the
 * script execution.
 *
 * @version 0.1
 */
@Service
public class JSExecutorService {

    private final int TIME_TO_INTERRUPT = 5;
    private final TimeUnit TIME_SCALE = TimeUnit.SECONDS;
    private final int THREAD_POOL_SIZE = 4;

    private ScheduledExecutorService pool = Executors.newScheduledThreadPool(THREAD_POOL_SIZE);

    @Autowired
    JSExecutor executor;

    public JSExecutionResultHttpResponseDto execute(@NonNull String script) {
        Future<JSExecutionResultDto> scriptTask = pool.submit(
                () -> executor.execute(script)
        );

        pool.schedule(
                () -> scriptTask.cancel(true),
                TIME_TO_INTERRUPT,
                TIME_SCALE
        );

        return buildJsExecutionResultHttpResponseDto(scriptTask);
    }

    // It is necessary to clarify the HttpStatus codes in InterruptedException and ExecutionException cases.
    private JSExecutionResultHttpResponseDto buildJsExecutionResultHttpResponseDto(Future<JSExecutionResultDto> scriptTask) {
        JSExecutionResultHttpResponseDto response = new JSExecutionResultHttpResponseDto();
        JSExecutionResultDto executionResult = new JSExecutionResultDto();
        HttpStatus statusCode;

        try {
            executionResult = scriptTask.get();
            statusCode = executionResult.getErrors().length() == 0 ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        } catch (CancellationException e) {
            response.setExecutionResult(e.toString());
            statusCode = HttpStatus.GATEWAY_TIMEOUT;
        } catch (ExecutionException | InterruptedException e) {
            response.setExecutionResult(e.toString());
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return buildJsExecutionResultHttpResponseDto(executionResult, statusCode);
    }

    private JSExecutionResultHttpResponseDto buildJsExecutionResultHttpResponseDto(JSExecutionResultDto dto, HttpStatus statusCode) {
        return JSExecutionResultHttpResponseDto
                .builder()
                .executionResult(dto.getExecutionResult() + dto.getErrors())
                .httpStatusCode(statusCode)
                .build();
    }
}
