package com.jsrunner.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.net.URISyntaxException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JSReceiverControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void termsOfUse() {
        //prepare
        String expected = "To execute JavaScript script you should pass it in the body of request with next configuration<br>" +
                "1) RequestMethod : POST <br>" +
                "2) Content-Type : application/javascript";
        //testing
        ResponseEntity<String> response = restTemplate.exchange("/js", HttpMethod.GET, null, String.class);
        String body = response.getBody();
        //validate
        assertThat(expected, is(body));
    }

    @Test
    public void execute() throws URISyntaxException {
        //prepare
        String expected = "Hello world!\r\n";
        String expectedStatusCode = "200";
        String requestScriptBody = "print(\"Hello world!\")";
        RequestEntity<String> requestEntity = RequestEntity.post(new URI("/js"))
                .header("Content-Type", "application/javascript")
                .body(requestScriptBody);
        //testing
        ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);
        String body = response.getBody();
        String statusCode = response.getStatusCode().toString();
        //validate
        assertThat(statusCode, is(expectedStatusCode));
        assertThat(body, is(expected));
    }
}