<!DOCTYPE html>
<html ng-app="jsRunnerApp1">
<head>
    <meta charset="utf-8">
    <title>Test page</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/lib/angular.min.js"></script>
    <script src="js/lib/scrollglue.js"></script>
    <script src="js/app.js"></script>
</head>
<body>
<body ng-controller="LoadController">
<div align="center">
    <br/>
    <textarea
            readonly
            style="
            resize: none;
            height: 300px;
            width: 640px;
            color:white;
            background-color: #637D8B"
            type="text"
            scroll-glue="glued"
            ng-model="response">
</textarea>
    <br/>
    <button class="btn" ng-click="glued=(!glued)" style="margin-right: 10px">
        Отслеживать({{glued}})
    </button>
    <button class="btn btn-success" ng-click="start()" style="margin-right: 10px">Start</button>
    <button class="btn btn-danger" ng-click="stop()" style="margin-right: 10px">Stop</button>
</div>

<div>
    <label>Input</label>
    <textarea ng-model="scriptsource">
    </textarea>
    <br>
    <button ng-click="send()">Send</button>
    <br>
    <label>Output</label>
    <textarea ng-model="scriptoutput">
    </textarea>
</div>

</body>
</html>