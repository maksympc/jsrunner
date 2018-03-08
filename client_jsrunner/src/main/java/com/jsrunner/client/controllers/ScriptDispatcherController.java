package com.jsrunner.client.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class ScriptDispatcherController {

    private RestTemplate restTemplate;

    @GetMapping("/")
    public String getIndex() {
        return "index";
    }

}