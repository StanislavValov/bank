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

    $http.get('/bankController').
        success(function (result) {
            $scope.currentAmount = result;
        });


    $scope.deposit = function () {
        $http.post('/bankController', $scope.account).
            success(function (result) {
                $scope.currentAmount = result;
                $scope.transactionError = '';
            }).
            error(function () {
                $scope.transactionError = 'Transaction Error';
            });
    };

    $scope.withdraw = function () {
        $http.put('/bankController', $scope.account).
            success(function (result) {
                $scope.currentAmount = result;
                $scope.transactionError = '';
            }).
            error(function () {
                $scope.transactionError = 'Transaction Error';
            });
    };
}]);

httpModule.controller('LogoutController', ['$scope', '$http', function ($scope, $http) {

    $scope.logout = function () {
        $http.post('/logout', '').
            success(function () {
                window.location.href = 'Index.html';
            });
    };
}]);

httpModule.controller('RegistrationController', ['$scope', '$http', '$state', function ($scope, $http, $state) {

    $scope.userName = '';
    $scope.password = '';

    $scope.credentials = {
        userName: $scope.userName,
        password: $scope.password
    };
    $scope.register = function () {

        $http.post('/registration', $scope.credentials).
            success(function () {
                window.location.href = 'Index.html';
            }).
            error(function () {
                $scope.registrationError = 'Registration failed';
                $state.go('registration')
            });
    };
}]);


httpModule.config(function ($stateProvider) {

    $stateProvider
        .state("user", {
            url: '/user',
            views: {
                '': { templateUrl: 'User.html'}

            }
        }).state("login", {
            url: '/error',
            views: {
                '': {templateUrl: 'Login.html',
                    template: 'Wrong username or password'}
            }

        }).state("registration", {
            url: '/registration',
            views: {
                '': {templateUrl: 'RegistrationForm.html'}
            }
        });
});