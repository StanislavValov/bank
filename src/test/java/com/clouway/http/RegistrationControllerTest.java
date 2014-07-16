package com.clouway.http;

import com.clouway.core.AccountService;
import com.clouway.core.BankValidator;
import com.clouway.core.LabelMap;
import com.clouway.core.SiteMap;
import com.clouway.core.User;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public class RegistrationControllerTest {

    Mockery context = new JUnit4Mockery();
    RegistrationController registrationController;
    User user;

    AccountService accountService = context.mock(AccountService.class);
    BankValidator bankValidator = context.mock(BankValidator.class);
    SiteMap siteMap = context.mock(SiteMap.class);

    @Before
    public void setUp() throws Exception {
        user = new User("Stanislav","123456");
        user.setPassword("123456");
        registrationController = new RegistrationController(accountService, bankValidator, siteMap);
        registrationController.setUser(user);
    }

    @Test
    public void createAccountSuccessful() {

        context.checking(new Expectations() {
            {
                oneOf(bankValidator).isUserCorrect(user);
                will(returnValue(true));

                oneOf(accountService).userExists(user);
                will(returnValue(false));

                oneOf(accountService).registerUser(user);

                oneOf(siteMap).loginForm();
                will(returnValue("/bank/Login.html"));
            }
        });
        assertThat(registrationController.register(), is("/bank/Login.html"));
    }

    @Test
    public void validateDataFailed() {

        context.checking(new Expectations() {
            {
                oneOf(bankValidator).isUserCorrect(user);
                will(returnValue(false));

                oneOf(siteMap).registrationForm();
                will(returnValue("/registration"));
            }
        });
        assertThat(registrationController.register(),is("/registration"));
    }

    @Test
    public void userAlreadyExists() {

        context.checking(new Expectations() {
            {
                oneOf(bankValidator).isUserCorrect(user);
                will(returnValue(true));

                oneOf(accountService).userExists(user);
                will(returnValue(true));

                oneOf(siteMap).registrationForm();
                will(returnValue("/registration"));
            }
        });
        assertThat(registrationController.register(),is("/registration"));
    }
}