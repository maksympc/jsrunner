let app = angular.module("jsRunnerApp", ['luegg.directives']);

app.factory("dataService",
    function ($http, $q) {
        return {
            getData: function () {
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
            }
        }
    }
);

app.controller("LoadController",
    function LoadController($scope, dataService, $interval) {
        $scope.glued = true;
        $interval(() => {
            let lProm = dataService.getData();
            lProm.then((value) => $scope.response = value);
        }, 100);
    }
);
