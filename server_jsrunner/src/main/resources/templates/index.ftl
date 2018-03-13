<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/index.css">
    <title>JS Runner. Server</title>
</head>
<body>
<div class="container">
    <div class="col-md-12">

    <#-- PAGE HEADER-->
        <div class="page-header text-center">
            <h2>JS Runner project</h2>
        </div>

        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">User guide:</h3>
            </div>
            <div class="panel-body">
                <p>To execute the JavaScript code, create and send to the address {root}/js a request with the following
                    content:</p>
                <div class="table-responsive">
                    <table class="table table-striped table-bordered">
                        <thead>
                        <tr>
                            <td>
                                #
                            </td>
                            <td>
                                Parameter
                            </td>
                            <td>
                                Value
                            </td>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>
                                1
                            </td>
                            <td>
                                HTTP Method
                            </td>
                            <td>
                                POST
                            </td>
                        </tr>
                        <tr>
                            <td>
                                2
                            </td>
                            <td>
                                h:Content-Type
                            </td>
                            <td>
                                application/json
                            </td>
                        </tr>
                        <tr>
                            <td>
                                3
                            </td>
                            <td>
                                b:mode
                            </td>
                            <td>
                                SYNC/ASYNC
                            </td>
                        </tr>
                        <tr>
                            <td>
                                4
                            </td>
                            <td>
                                b:sourceSource
                            </td>
                            <td>
                                script source code...
                            </td>
                        </tr>
                        <tr>
                            <td colspan="3">
                                *h - header element, b - body element
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <div class="panel-group">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h4 class="panel-title">
                                <a data-toggle="collapse" href="#exampleID">Example:</a>
                            </h4>
                        </div>
                        <div id="exampleID" class="panel-collapse collapse">
                            <div class="panel-body">
<pre>
        POST http://localhost:8090/js HTTP/1.1
        Content-Type: application/json

        {
            "mode":"ASYNC",
            "sourceCode":"print('Hello world');"
        }
</pre>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>


    </div>
</div>
<div class="footer">
    <div class="container text-center">
        <p class="text-muted"><a target="_blank" href="https://github.com/maksympc/jsrunner"> © 2018 JS Runner.</a> All
            rights reserved.</p>
    </div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>