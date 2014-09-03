var httpModule = angular.module('httpModule', ['ui.router']);

httpModule.controller('LoginController', ['$scope', '$http', '$state',
    function ($scope, $http, $state) {

        $scope.userName = '';
        $scope.password = '';

        $scope.credentials = {
            userName: $scope.userName,
            password: $scope.password
        };


        $scope.login = function () {

            $http.post('/login', $scope.credentials).
                success(function () {
                    $state.go('user');
                }).
                error(function () {
                    $state.go('login');
                });
        };
    }]);

httpModule.controller('BankController', ['$scope', '$http', function ($scope, $http) {

    $scope.transactionAmount = '';
    $scope.currentAmount = '';

    $scope.account = {
        transactionAmount: $scope.transactionAmount
    };

    $scope.deposit = function () {

        $http.post('/bankController', $scope.account).
            success(function () {
//                window.location.reload();
            }).
            error(function () {
                window.location.href = 'TransactionError.html';
            });
    };

    $scope.withdraw = function () {
        $http.put('/bankController', $scope.account).
            success(function () {
//                window.location.reload();
            }).
            error(function () {
                window.location.href = 'TransactionError.html';
            });
    };

    $scope.$watch($scope.transactionAmount, function () {
        $http.get('/bankController').
            success(function (result) {
                $scope.currentAmount = result;
            });
    });
}]);

httpModule.controller('LogoutController', ['$scope', '$http', function ($scope, $http) {

    $scope.logout = function () {
        $http.post('/logout', '').
            success(function () {
                window.location.href = 'Login.html';
            });
    };
}]);

httpModule.controller('RegistrationController', ['$scope', '$http', '$state', function ($scope, $http, $state) {

    $scope.register = function () {

        $http.jsonp('/registration', $scope.credentials).
            success(function () {
                $state.go('registration');
            }).
            error(function () {
                window.location.href = 'RegistrationError.html';
            });
    };
}]);

httpModule.config(function ($stateProvider) {

    $stateProvider.state("user", {
        url: '/user',
        views: {
            'user@user': { templateUrl: 'Login.html',
                controller: 'LoginController'}
        }
    }).state("login", {
        url: '/error',
        views: {
            '': {templateUrl: 'Login.html',
                template: 'Wrong username or password'},
            'error@login': {
//                controller: 'LoginController',

            }
        }
    }).state("registration", {
        url: '/registration',
        views: {
//            '': {templateUrl: 'Login.html'},
            '': {templateUrl: 'RegistrationForm.html'}
        }
    })
});