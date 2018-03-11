package com.jsrunner.client.controllers;

import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import javax.annotation.PostConstruct;

@Controller
public class ScriptDispatcherController {

    public static ResponseDTO response = new ResponseDTO();

    @PostConstruct
    public void runProducer() {
        Producer producer = new Producer(response);
        producer.start();
    }

    @GetMapping("/test")
    public String getTest() {
        return "test";
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String getIndex() {
        return "index";
    }

    @RequestMapping(path = "/storage", method = RequestMethod.GET)
    @ResponseBody
    public DeferredResult<ResponseEntity<ResponseDTO>> getStorage() {
        DeferredResult<ResponseEntity<ResponseDTO>> response = new DeferredResult<>();
        response.setResult(ResponseEntity.ok().body(ScriptDispatcherController.response));
        return response;
    }

    @Data
    public static class ResponseDTO {
        private StringBuilder executionResult = new StringBuilder();

        public void append(String s) {
            executionResult.append(s);
        }
    }

    class Producer extends Thread {

        private ResponseDTO response;

        Producer(ResponseDTO response) {
            this.response = response;
        }

        @Override
        public void run() {
            int i = -1;
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i = ++i % STORAGE.length;
                this.response.append(STORAGE[i]);
            }
        }
    }

    private static final String[] STORAGE =
            ("1. “Sonnet 18” by William Shakespeare (1564-1616)\n\n" +
                    "Shall I compare thee to a summer’s day?\n" +
                    "Thou art more lovely and more temperate:\n" +
                    "Rough winds do shake the darling buds of May,\n" +
                    "And summer’s lease hath all too short a date:\n" +
                    "Sometime too hot the eye of heaven shines,\n" +
                    "And often is his gold complexion dimm’d;\n" +
                    "And every fair from fair sometime declines,\n" +
                    "By chance, or nature’s changing course, untrimm’d;\n" +
                    "But thy eternal summer shall not fade\n" +
                    "Nor lose possession of that fair thou ow’st;\n" +
                    "Nor shall Death brag thou wander’st in his shade,\n" +
                    "When in eternal lines to time thou grow’st;\n" +
                    "So long as men can breathe or eyes can see,\n" +
                    "So long lives this, and this gives life to thee.\n\n").split("(?!^)");

}
