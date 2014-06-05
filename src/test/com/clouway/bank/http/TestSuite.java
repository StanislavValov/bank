package com.clouway.bank.http;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */

import com.clouway.bank.http.BankControllerTest;
import com.clouway.bank.http.LoginControllerTest;
import com.clouway.bank.http.RegistrationControllerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        BankControllerTest.class,
        LoginControllerTest.class,
        RegistrationControllerTest.class
})
public class TestSuite {
}
