package com.clouway.http;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        BankControllerTest.class,
//        LoginControllerTest.class,
//        RegistrationControllerTest.class,
        LogoutControllerTest.class,
//        SecurityFilterTest.class
})
public class TestSuite {
}