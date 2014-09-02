package com.clouway.http;

import com.clouway.core.*;
import com.google.inject.Provider;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public class BankControllerTest {

    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    BankController bankController = null;
    User user;
    Session session;
    Account account;

    BankService bankService = context.mock(BankService.class);
    BankValidator bankValidator = context.mock(BankValidator.class);
    Provider provider = context.mock(Provider.class);
    SiteMap siteMap = context.mock(SiteMap.class);

    @Before
    public void setUp() throws Exception {
        session = new Session("Stan","123",new Date());
        user = new User();
        account = new Account();
        bankController = new BankController(bankService, bankValidator, provider);
    }

    @Test
    public void transactionAmountIsNotCorrect() {

        context.checking(new Expectations() {
            {
                oneOf(provider).get();
                will(returnValue(session));

                oneOf(bankValidator).transactionIsValid(null);
                will(returnValue(false));

                oneOf(siteMap).transactionError();
                will(returnValue("/bank/TransactionError.html"));
            }
        });
//        assertThat(bankController.accountOperation(), is("/bank/TransactionError.html"));
    }

    @Test
    public void depositSuccess() {

        context.checking(new Expectations() {
            {
                oneOf(provider).get();
                will(returnValue(session));

                oneOf(bankValidator).transactionIsValid(null);
                will(returnValue(true));

                oneOf(bankService).deposit(session, "5");


                oneOf(siteMap).bankController();
                will(returnValue("/bankController"));
            }
        });
//        assertThat(bankController.accountOperation(), is("/bankController"));
    }

    @Test
    public void withdrawSuccess() {

        context.checking(new Expectations() {
            {
                oneOf(provider).get();
                will(returnValue(session));

                oneOf(bankValidator).transactionIsValid(null);
                will(returnValue(true));

                oneOf(bankService).withdraw(session, "5");

                oneOf(siteMap).bankController();
                will(returnValue("/bankController"));
            }
        });
//        assertThat(bankController.accountOperation(), is("/bankController"));
    }
}