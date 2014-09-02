describe('httpModule', function () {

    beforeEach(module('httpModule'));

    describe('Login functionality', function () {

        var scope,httpBackend;
        beforeEach(inject(function ($rootScope, $controller, $httpBackend, $http) {

            scope = $rootScope.$new();
            httpBackend = $httpBackend;
            httpBackend.when("POST", '/login').respond(500);
            $controller('LoginController', {
               $scope: scope,
               $http: $http
            });
        }));

        it('should fail to login', function () {
            httpBackend.flush();
            expect(window.location.href).toBe('AuthenticationError.html');
        });
    });
});