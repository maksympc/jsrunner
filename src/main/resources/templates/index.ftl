<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

    <link rel="stylesheet" href="css/index.css">
    <link rel="stylesheet" href="css/prism.css">
    <title>JS Runner Project. Main page</title>
</head>
<body>
<div class="container">
    <div class="col-md-12">
    <#-- PAGE HEADER-->
        <div class="page-header text-center">
            <h2>JS Runner project</h2>
        </div>
    <#--LIST OF SCRIPTS-->
        <div class="panel-group">
            <div class="panel panel-default">
                <div class="panel-heading panel-title" style="height:50px">
                <#--LIST OF SCRIPTS HEAD-->
                    <div class="form-inline panel-title">
                        <a data-toggle="collapse" href="#listOfScripts"><b>List of scripts</b></a>
                        <button type="button" class="btn btn-success pull-right"
                                style="width:90px;">Add new +
                        </button>
                        <select class="form-control pull-right" style="width:130px;margin-right: 10px"
                                id="scriptingMode">
                            <option>Blocking</option>
                            <option>Non-blocking</option>
                        </select>
                    </div>
                </div>
            <#--LIST OF SCRIPTS BODY-->
                <div id="listOfScripts" class="panel-collapse collapse">
                    <ul class="list-group">
                        <li class="list-group-item">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h4 class="panel-title">
                                        <a data-toggle="collapse" href="#scriptarea1">Script 1</a>
                                    </h4>
                                </div>
                                <div id="scriptarea1" class="panel-collapse collapse">
                                    <div class="panel-body">
                                        <figure class="col-md-6">
                                            <figcaption>Input:</figcaption>
                                            <pre>
                                                <code contenteditable class="line-numbers language-javascript">
function myPrint(){
    print("Hello world!");
}
function myPrint(){
    print("Hello world!");
}
function myPrint(){
    print("Hello world!");
}
                                                </code>
                                            </pre>
                                        </figure>
                                        <figure class="col-md-6">
                                            <figcaption>Output:</figcaption>
                                            <pre>
                                                <code class="line-numbers language-c-like">
function myPrint(){
    print("Hello world!");
}
function myPrint(){
    print("Hello world!");
}
function myPrint(){
    print("Hello world!");
}
                                                </code>
                                            </pre>
                                        </figure>
                                    </div>
                                </div>
                            </div>
                        </li>
                        <li class="list-group-item">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h4 class="panel-title">
                                        <a data-toggle="collapse" href="#scriptarea2">Script 2</a>
                                    </h4>
                                </div>
                                <div id="scriptarea2" class="panel-collapse collapse">
                                    <div class="panel-body">Panel Body</div>
                                </div>
                            </div>
                        </li>
                        <li class="list-group-item">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h4 class="panel-title">
                                        <a data-toggle="collapse" href="#scriptarea3">Script 3</a>
                                    </h4>
                                </div>
                                <div id="scriptarea3" class="panel-collapse collapse">
                                    <div class="panel-body">Panel Body</div>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
                <div class="panel-footer"></div>
            </div>
        </div>
    </div>
</div>

<script src="js/prism.js"></script>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>