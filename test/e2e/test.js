describe('angularJSBank', function () {


    describe('Authorisation functionality', function () {

        var userName = element(by.model('credentials.userName'));
        var password = element(by.model('credentials.password'));

        it('should fail to login', function () {

            browser.get('/bank/Login.html');

            userName.sendKeys('');
            password.sendKeys('');

            element(by.id('login')).click();

            expect(browser.getCurrentUrl()).toBe('http://localhost:8080/bank/AuthenticationError.html');
        });

        it('should login into User.html with user credentials', function () {

            browser.get('/bank/Login.html');

            userName.sendKeys('Brahmaputra');
            password.sendKeys('123456');

            element(by.id('login')).click();

            expect(browser.getCurrentUrl()).toBe('http://localhost:8080/bank/User.html');

        });
    });


    describe('Bank functionality', function () {

        var transactionAmount = element(by.model('account.transactionAmount'));

        it('should deposit money to user', function () {

            transactionAmount.sendKeys('20');

            element(by.id('deposit')).click();

        });

        it('should withdraw money from user', function () {

            transactionAmount.sendKeys('20');

            element(by.id('withdraw')).click();
        });

        it('should redirect to TransactionError.html when user make attempt to deposit incorrect value', function () {

            transactionAmount.sendKeys('1.111');

            element(by.id('deposit')).click();

            expect(browser.driver.getCurrentUrl()).toBe('http://localhost:8080/bank/TransactionError.html');
        });
    });

    describe('Logout functionality', function () {

        it('should logout from User.html page and redirect to Login.html page', function () {

            browser.get('/bank/User.html');

            element(by.id('logout')).click();

            expect(browser.driver.getCurrentUrl()).toBe('http://localhost:8080/bank/Login.html');
        });
    });
});