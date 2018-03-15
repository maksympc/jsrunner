package com.jsrunner.client.services;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jsrunner.client.models.*;
import com.jsrunner.client.repository.MemoryStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;


// TODO: поправить обработку исключени, совместить сервис с AsyncJsRunnerService
@Service
public class AsyncJsRunnerClientApi {

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private MemoryStorage db;

    @Value("${app.jsrunner.apiUrl}")
    private String url;
    private Gson converter = new Gson();

    @PostConstruct
    public void runHelper(){
        ScriptExecutionQueueHelperThread consumer = new ScriptExecutionQueueHelperThread();
        applicationContext.getAutowireCapableBeanFactory().autowireBean(consumer);
        consumer.start();
    }

    private RestTemplate restTemplate = new RestTemplate();

    public void processRequest(UUID id, ScriptRequestDto script) {
        HttpEntity<ScriptRequestDto> request = new HttpEntity<>(script);
        ResponseEntity<String> rawResponse = restTemplate.exchange(url + "/js", HttpMethod.POST, request, String.class);
        try {
            JsRunnerResponseDto postResponse = converter.fromJson(rawResponse.getBody(), JsRunnerResponseDto.class);
            getUntilFinished(id, postResponse);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
    }

    private void getUntilFinished(UUID id, JsRunnerResponseDto requestContainer) {
        Object getResponse;
        while (true) {
            ResponseEntity<String> rawResponse = restTemplate.getForEntity(url + requestContainer.getLocation(), String.class);
            System.out.println("UNTIL FINISHED rawResponse:" + rawResponse.getBody());
            getResponse = deserializeToSuitableResponse(rawResponse.getBody());

            //TODO:for testing
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (getResponse instanceof JsRunnerItemResponseDto) {
                JsRunnerItemResponseDto convertedResponse = (JsRunnerItemResponseDto) getResponse;
                if (convertedResponse.getStatus().equals(ScriptExecutionStatus.NEW) ||
                        convertedResponse.getStatus().equals(ScriptExecutionStatus.QUEUED) ||
                        convertedResponse.getStatus().equals(ScriptExecutionStatus.RUNNING)) {
                    //TODO:check optional converting
                    ScriptExecutionItemResponseDto dbItem = db.get(id).get();
                    dbItem.setStatus(convertedResponse.getStatus());
                    dbItem.setOutput(convertedResponse.getOutput());
                    //TODO:testing output
                    System.out.println("Updating item with id={" + id + "} ...");
                }
            } else if (getResponse instanceof JsRunnerResponseDto) {
                JsRunnerResponseDto localRequest = (JsRunnerResponseDto) getResponse;
                ResponseEntity<String> localRaw = restTemplate.getForEntity(url + localRequest.getLocation(), String.class);
                JsRunnerItemResponseDto convertedResponse = (JsRunnerItemResponseDto) deserializeToSuitableResponse(localRaw.getBody());
                //TODO:
                ScriptExecutionItemResponseDto dbItem = db.get(id).get();
                dbItem.setStatus(convertedResponse.getStatus());
                dbItem.setOutput(convertedResponse.getOutput());
                //TODO:testing output
                System.out.println("End processing with response:" + getResponse);
                return;
            }
        }
    }

    //TODO: check deserialization process
    private Object deserializeToSuitableResponse(String rawResponse) {
        try {
            JsRunnerResponseDto r0 = converter.fromJson(rawResponse, JsRunnerResponseDto.class);
            if (Optional.ofNullable(r0.getLocation()).isPresent() || Optional.ofNullable(r0.getErrors()).isPresent()) {
                return r0;
            }
            JsRunnerItemResponseDto r1 = converter.fromJson(rawResponse, JsRunnerItemResponseDto.class);
            if (Optional.ofNullable(r1.getStatus()).isPresent()) {
                return r1;
            }
        } catch (Exception e) {
            return new DefaultResponseDto(rawResponse + e.toString());
        }
        return new DefaultResponseDto(rawResponse);
    }

    /**
     * This class is used for getting script from execution queue
     * and forward them to ScheduleExecutorService for further executing
     */
    private class ScriptExecutionQueueHelperThread extends Thread {

        @Override
        public void run() {
            BlockingQueue<ScriptExecutionItem> scriptsQueue = db.getRequestsQueue();
            while (true) {
                try {
                    ScriptExecutionItem item = scriptsQueue.take();
                    processRequest(item.getId(), item.getScript());
                } catch (Exception e) {
                    //TODO
                    e.printStackTrace();
                }
            }
        }
    }

}

