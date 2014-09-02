var httpModule = angular.module('httpModule', []);

httpModule.controller('LoginController', ['$scope', '$http',
    function ($scope, $http) {

        $scope.userName = '';
        $scope.password = '';

        $scope.credentials = {
            userName: $scope.userName,
            password: $scope.password
        };

        $scope.login = function () {

            $http.post('/login', $scope.credentials).
                success(function () {
                    window.location.href = 'User.html';
                }).
                error(function () {
                    window.location.href = 'AuthenticationError.html';
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
                window.location.reload();
            }).
            error(function () {
                window.location.href = 'TransactionError.html';
            });
    };

    $scope.withdraw = function () {
      $http.put('/bankController', $scope.account).
          success(function () {
              window.location.reload();
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

httpModule.controller('RegistrationController', ['$scope', '$http', function ($scope, $http) {

    $scope.register = function () {

        $http.jsonp('/registration', $scope.credentials).
            success(function () {
                window.location.href = 'Login.html';
            }).
            error(function () {
                window.location.href = 'RegistrationError.html';
            });
    };
}]);