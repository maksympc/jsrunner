# JSRunner
<p>App is under development.
<p><b>JSRunner Server:</b>
<br/>It is a REST API wrapper around javax.script.ScriptEngine, that provides capabilities via API:
<br/>1) Run the JavaScript code in the body of the request in Nashorn (java 8) and return the output of the script (or error message) to the console in the response body.
<br/>2) View the status of the script (completed successfully, with an error, executed, in the queue) and its console output.
<br/>3) Complete the hung scripts forcibly.
<p>Requests can come in parallel, and the script can be executed for a long time, and maybe even hang, for example, in an infinite loop. These cases are handled correctly.
<br/>This app based on Spring Boot and asynchronous REST API approach.
<br/><p><b>JSRunner Client:</b>
<br/>Standalone application, based on Spring Boot and AngularJS frameworks. It provides user-friendly interface to use the capabilities of the JSRunner Server application.
