<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/index.css">
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
            <div class="panel panel-default" style="
                 position: absolute;
                 left: 50%;
                 width:95%;
                 transform: translateX(-50%);
                 resize: horizontal;
                 overflow:auto;
                 min-width:480px">
                <div class="panel-heading panel-title" style="height:50px">
                <#--LIST OF SCRIPTS HEAD-->
                    <div class="form-inline panel-title">
                        <a data-toggle="collapse" href="#listOfScripts">List of scripts</a>
                        <div class="form-inline pull-right">
                            <select class="form-control" style="width:130px;margin-right: 10px"
                                    id="scriptingMode">
                                <option>Blocking</option>
                                <option>Non-blocking</option>
                            </select>
                            <button type="button" class="btn btn-success"
                                    style="width:90px;">Add new +
                            </button>
                        </div>
                    </div>
                </div>
            <#--LIST OF SCRIPTS BODY-->
                <div id="listOfScripts" class="panel-collapse collapse">
                    <ul class="list-group">
                    <#--First sourceCode-->
                        <li class="list-group-item">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h4 class="panel-title">
                                        <a data-toggle="collapse" href="#scriptarea1">Script 1</a>
                                    </h4>
                                </div>
                                <div id="scriptarea1" class="panel-collapse collapse">
                                    <div class="panel-body" min-height: 250px;
                                    ">
                                    <div class="form-group col-md-12"
                                         style="min-height:120px; resize: vertical; overflow:auto;">
                                    <#--INPUT script form. GET AND SEND parameters from here-->
                                        <form style="height: 100%">
                                            <label for="comment">Input:</label>
                                            <textarea class="form-control" id="scriptInputId1"
                                                      placeholder="print('Hello world!');"
                                                      style="resize: none;
                                                      height: calc(100% - 60px);"></textarea>
                                            <button type="button" class="btn btn-default btn-sm"
                                                    style="margin-top: 5px; width:100px"
                                                    onclick="copyInput()">Copy text
                                            </button>
                                            <div class="pull-right">
                                                <button type="button" class="btn btn-sm btn-danger"
                                                        style="margin-top: 5px; width:100px">
                                                    Stop
                                                </button>
                                                <button type="button" class="btn btn-sm btn-success "
                                                        style="margin-top: 5px; width:100px">Run
                                                </button>
                                            </div>
                                        </form>
                                    </div>
                                <#--OUTPUT script form. GET AND SEND parameters from here-->
                                    <div class="form-group col-md-12"
                                         style="min-height:120px; resize: vertical; overflow:auto;">
                                        <label for="comment">Output:</label>
                                        <textarea
                                                onclick="this.focus();this.select()"
                                                readonly
                                                class="form-control" id="scriptOutputId1"
                                                style="
                                                        resize: none;
                                                        height: calc(100% - 60px);
                                                        color:white;
                                                        background-color: #637D8B"
                                                placeholder="Hello world!"></textarea>
                                        <button type="button" class="btn btn-default btn-sm"
                                                style="margin-top: 5px; width:100px"
                                                onclick="copyOutput()">Copy text
                                        </button>
                                    </div>
                                </div>
                        </li>
                    <#--First sourceCode Ending-->
                        <li class="list-group-item">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h4 class="panel-title">
                                        <a data-toggle="collapse" href="#scriptarea2">Script 2</a>
                                    </h4>
                                </div>
                                <div id="scriptarea2" class="panel-collapse collapse"
                                     style="resize: vertical; overflow:auto;">
                                    <div class="panel-body">
                                        Panel body
                                    </div>
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

<div class="footer">
    <div class="container text-center">
        <p class="text-muted"><a target="_blank" href="https://github.com/maksympc/jsrunner"> © 2018 JS Runner.</a> All
            rights reserved.</p>
    </div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<script>
    function copyOutput() {
        var copyText = document.getElementById("scriptOutputId1");
        copyText.select();
        if (copyText.value != "") {
            document.execCommand("Copy");
        }
    }

    function copyInput() {
        var copyText = document.getElementById("scriptInputId1");
        copyText.select();
        if (copyText.value != "") {
            document.execCommand("Copy");
        }
    }
</script>
</body>
</html>