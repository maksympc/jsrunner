package com.jsrunner.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


@RunWith(SpringRunner.class)
public class JavaScriptExecutorServiceTest {

    private JavaScriptExecutorService executorService = new JavaScriptExecutorService();
    private static final String FILENAME = "src/test/resources/HelloWorld.js";

    @Test
    public void execute() throws IOException, ScriptException {
        String script = new String(Files.readAllBytes(Paths.get(FILENAME)));
        executorService.execute(script);
    }

}