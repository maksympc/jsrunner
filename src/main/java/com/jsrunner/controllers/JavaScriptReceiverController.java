package com.jsrunner.controllers;


import com.jsrunner.services.JavaScriptExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class is used to receive requests for ("/js") mapping. It's an entry point of API to execute JavaScript's scripts
 *
 * @version 0.2
 */
@RestController
@RequestMapping("/js")
public class JavaScriptReceiverController {

    @Autowired
    private JavaScriptExecutorService executorService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> termsOfUse() {
        String termsOfUse = "To execute JavaScript script you should pass it in the body of request with next configuration<br>" +
                "1) RequestMethod : POST <br>" +
                "2) Content-Type : application/javascript";
        return ResponseEntity.accepted().header("Content-Type", "text/html").body(termsOfUse);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> execute(@RequestBody String script) {
        String result = executorService.execute(script);
        return ResponseEntity.accepted().body(result);
    }
}
