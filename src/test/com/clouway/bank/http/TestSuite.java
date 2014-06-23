package com.clouway.bank.http;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        BankControllerTest.class,
        LoginControllerTest.class,
        RegistrationControllerTest.class,
        LogoutController.class,
        SecurityFilter.class,
        UserAccountController.class
})
public class TestSuite {
}
