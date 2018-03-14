package com.jsrunner.client.services;

import com.google.gson.Gson;
import com.jsrunner.client.models.JsRunnerItemResponseDto;
import com.jsrunner.client.models.ScriptRequestDto;
import com.jsrunner.client.models.JsRunnerResponseDto;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class JsRunnerApiClient {
    @Value("${app.jsrunner.apiUrl}")
    private String url;

    RestTemplate template = new RestTemplate();

    public String send(@NonNull String script) {

        ScriptRequestDto request = ScriptRequestDto
                .builder()
                .mode("ASYNC")
                .sourceCode("print('Hello world!')")
                .build();

        ResponseEntity<String> rawResponse = template.postForEntity(url + "/js", request, String.class);
        JsRunnerResponseDto response = new Gson().fromJson(rawResponse.getBody(), JsRunnerResponseDto.class);
        System.out.println("first:" + response);
        ResponseEntity<String> secondRaw = template.getForEntity(url + response.getLocation(), String.class);
        JsRunnerItemResponseDto secondResponse = new Gson().fromJson(secondRaw.getBody(), JsRunnerItemResponseDto.class);
        System.out.println("second:" + secondResponse);
        return "Success";
    }

}
