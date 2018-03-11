<!DOCTYPE html>
<html ng-app="jsRunnerApp">
<head>

    <meta charset="utf-8">
    <title>Test page</title>
    <link rel="stylesheet" href="css/lib/bootstrap.min.css">
    <script src="js/lib/angular.min.js"></script>
    <script src="js/lib/scrollglue.js"></script>
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

<script src="js/app.js"></script>
</body>
</html>
