var myApp = angular.module('jsRunnerApp1', ['luegg.directives']);

myApp.controller('LoadController',
    function LoadController($scope, $http) {
        $scope.glued = true;

        // $scope.stop = () => {
        //     console.log('stop');
        // };
        //
        // $scope.start = () => {
        //     console.log('start');
        // };

        
        var data = angular.toJson({
            mode: 'ASYNC',
            sourceCode: $scope.scriptsource
        });
        var config = angular.toJson({
            headers: {
                'Content-type': 'application/json;'
            }
        });
        $scope.send = () => {
            $http.post('/js', data, config)
                .success(function (response, status, config) {
                    console.log(response.data);
                    $scope.scriptoutput = response.data;
                })
                .error(function (data, status, header, config) {
                    console.log(response.data);
                    $scope.scriptoutput = "Data: " + data +
                        "status: " + status +
                        "headers: " + header +
                        "config: " + config;
                });
        };

        // $scope.send = () => {
        //     console.log("Send was pressed!");
        //     $http({
        //         method: 'POST',
        //         url: 'http://localhost:8091/js',
        //         headers: {'Content-Type': 'application/json'},
        //         data: angular.toJson({
        //             mode: 'ASYNC',
        //             scriptSource: $scope.scriptsource
        //         })
        //     }).then(
        //         function (response) {
        //             console.log(response.toJson);
        //             $scope.output = response.data;
        //         },
        //         function (response) {
        //             console.log(response.toJson);
        //             $scope.output = response.data;
        //         })
        // };
    });
