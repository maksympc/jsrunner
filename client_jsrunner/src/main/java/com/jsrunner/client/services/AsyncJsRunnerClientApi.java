package com.jsrunner.client.services;


import com.jsrunner.client.models.JsRunnerResponseDto;
import com.jsrunner.client.models.ScriptRequestDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AsyncJsRunnerClientApi {

    @Value("${app.jsrunner.apiUrl}")
    private String url;

    private RestTemplate restTemplate = new RestTemplate();

    public JsRunnerResponseDto sendScript(ScriptRequestDto request) {
        JsRunnerResponseDto response = restTemplate.postForObject(url, request, JsRunnerResponseDto.class);
        System.out.println(response);
        return response;
    }
}

