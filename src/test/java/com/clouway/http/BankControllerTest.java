package com.clouway.http;

import com.clouway.core.*;
import com.google.inject.Provider;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public class BankControllerTest {

    Mockery context = new JUnit4Mockery();
    BankController bankController = null;
    User user;
    Account account;

    BankService bankService = context.mock(BankService.class);
    BankValidator bankValidator = context.mock(BankValidator.class);
    Provider provider = context.mock(Provider.class);
    SiteMap siteMap = context.mock(SiteMap.class);

    @Before
    public void setUp() throws Exception {
        user = new User();
        account = new Account();
        bankController = new BankController(bankService, bankValidator, provider, siteMap);
    }

    @Test
    public void transactionAmountIsNotCorrect() {

        context.checking(new Expectations() {
            {
                oneOf(provider).get();
                will(returnValue(user));

                oneOf(bankValidator).isAmountValid(null);
                will(returnValue(false));

                oneOf(siteMap).transactionAmountLabel();
                will(returnValue("amount"));

                oneOf(siteMap).transactionErrorLabel();
                will(returnValue("/bank/TransactionError.html"));
            }
        });
        assertThat(bankController.accountOperation(), is("/bank/TransactionError.html"));
    }

    @Test
    public void depositSuccess() {

        context.checking(new Expectations() {
            {
                oneOf(provider).get();
                will(returnValue(user));

                oneOf(bankValidator).isAmountValid(null);
                will(returnValue(true));

                oneOf(bankService).deposit(user, "5");

                oneOf(siteMap).bankController();
                will(returnValue("/bankController"));
            }
        });
        assertThat(bankController.accountOperation(), is("/bankController"));
    }

    @Test
    public void withdrawSuccess() {

        context.checking(new Expectations() {
            {
                oneOf(provider).get();
                will(returnValue(user));

                oneOf(bankValidator).isAmountValid(null);
                will(returnValue(true));

                oneOf(bankService).withdraw(user, "5");

                oneOf(siteMap).bankController();
                will(returnValue("/bankController"));
            }
        });
        assertThat(bankController.accountOperation(), is("/bankController"));
    }
}