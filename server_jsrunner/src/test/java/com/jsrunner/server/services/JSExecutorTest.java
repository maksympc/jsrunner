package com.jsrunner.server.services;

import static org.hamcrest.Matchers.is;

public class JSExecutorTest {

    JSExecutor executor;

//    @Test
//    public void execute() throws ScriptException {
//        //prepare
//        String expectedExecutionResult = "Hello world!\r\n";
//        String expectedErrors = "";
//        executor = new JSExecutor();
//        //testing
//        ScriptExecutionItem result = executor.execute("print(\"Hello world!\")");
//        //validate
//        assertThat(result.getErrors(), is(expectedErrors));
//        assertThat(result.getExecutionResult(), is(expectedExecutionResult));
//    }
//
//    @Test
//    public void executeBadFormattedScript() throws ScriptException {
//        //prepare
//        String expectedExecutionResult = "";
//        String[] expectedErrors = ("javax.sourceCode.ScriptException: <eval>:1:5 Expected ; but found t\r\n" +
//                "prin t(\"Hello world!\")\r\n" +
//                "     ^ in <eval> at line number 1 at column number 5").split("\\s");
//
//        executor = new JSExecutor();
//        //testing
//        ScriptExecutionItem result = executor.execute("prin t(\"Hello world!\")");
//        //validate
//        assertArrayEquals(expectedErrors, result.getErrors().split("\\s"));
//        assertThat(result.getExecutionResult(), is(expectedExecutionResult));
//    }

}