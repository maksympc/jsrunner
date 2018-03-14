let app = angular.module("jsRunnerApp", ['luegg.directives']);

app.factory("apiService",
    function ($http, $q, $scope) {
        return {
            getExecutionResponse: function () {
                let deferred = $q.defer();
                $http.get('/storage')
                    .then(
                        (response) => {
                            deferred.resolve(response.data.executionResult);
                        },
                        (response) => {
                            deferred.reject(response.status);
                        }
                    );
                return deferred.promise;
            },
            postScriptItem: function () {
                var data = angular.toJson({
                    mode: $scope.scriptMode,
                    sourceCode: $scope.scriptSourceCode
                });
                var config = angular.toJson({
                    headers: {
                        'Content-type': 'application/json;'
                    }
                });
                $http.post('/js', data, config)
                    .success(function (response, status, config) {
                        console.log(response);
                        $scope.scriptOutput = response.data;
                    })
                    .error(function (data, status, header, config) {
                        console.log(response);
                        $scope.scriptOutput = "Data: " + data +
                            "status: " + status +
                            "headers: " + header +
                            "config: " + config;
                    });
            }
        }
    }
);

app.controller("AppController",
    function AppController($scope, apiService, $interval) {
        $scope.glued = true;
        // $interval(() => {
        //     let lProm = dataService.getData();
        //     lProm.then((value) => $scope.response = value);
        // }, 100);

        $scope.scriptMode = "ASYNC";
        $scope.scriptSourceCode = "print('Hello world!')";
        apiService.postScriptItem();

        //$scope.postScriptItem = apiService.postScriptItem;
    }
);

