package com.jsrunner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * This class represents an entry point of application. It contains main method and top level configurations.
 *
 * @version 0.1
 */
@SpringBootApplication
@ComponentScan("com.jsrunner")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}