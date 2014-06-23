package com.clouway.bank.http;

import com.clouway.bank.core.*;
import com.google.inject.Provider;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public class BankControllerTest {

  Mockery context = new JUnit4Mockery();
  BankController bankController = null;
  User user;
  SiteMap siteMap;
  Account account;
  CurrentUser currentUser;

  HttpServletRequest request = context.mock(HttpServletRequest.class);
  HttpServletResponse response = context.mock(HttpServletResponse.class);
  BankService bankService = context.mock(BankService.class);
  BankValidator bankValidator = context.mock(BankValidator.class);
  Provider provider = context.mock(Provider.class);

  @Before
  public void setUp() throws Exception {
    siteMap = new LabelMap();
    account = new Account("10.101");
    user = new User("Ivan", account);
    currentUser = new CurrentUser(user);
    bankController = new BankController(bankService, bankValidator,provider,siteMap);
  }

  @Test
  public void amountIsNotCorrect() throws Exception {

    context.checking(new Expectations() {
      {
        oneOf(request).getParameter(siteMap.amountLabel());
        will(returnValue(account.getTransactionAmount()));

        oneOf(provider).get();
        will(returnValue(currentUser));

        oneOf(bankValidator).transactionIsValid(user);
        will(returnValue(false));

        oneOf(request).setAttribute(siteMap.amountErrorLabel(),siteMap.amountErrorMessage());

        oneOf(request).getRequestDispatcher(siteMap.transactionErrorLabel());
      }
    });
    bankController.doPost(request, response);
  }

  @Test
  public void depositSuccess() throws Exception {

    context.checking(new Expectations() {
      {
        oneOf(request).getParameter(siteMap.amountLabel());
        will(returnValue(account.getTransactionAmount()));

        oneOf(provider).get();
        will(returnValue(currentUser));

        oneOf(bankValidator).transactionIsValid(user);
        will(returnValue(true));

        oneOf(request).getParameter(siteMap.depositLabel());
        will(returnValue(siteMap.depositLabel()));

        oneOf(bankService).deposit(user);

        oneOf(request).getParameter(siteMap.withdrawLabel());
        will(returnValue(null));

        oneOf(response).sendRedirect(siteMap.successfulTransactionLabel());
      }
    });
    bankController.doPost(request, response);
  }

  @Test
  public void withdrawSuccess() throws Exception {

    context.checking(new Expectations(){
      {
        oneOf(request).getParameter(siteMap.amountLabel());
        will(returnValue(account.getTransactionAmount()));

        oneOf(provider).get();
        will(returnValue(currentUser));

        oneOf(bankValidator).transactionIsValid(user);
        will(returnValue(true));

        oneOf(request).getParameter(siteMap.depositLabel());
        will(returnValue(null));

        oneOf(request).getParameter(siteMap.withdrawLabel());
        will(returnValue(siteMap.withdrawLabel()));

        oneOf(bankService).withdraw(user);

        oneOf(response).sendRedirect(siteMap.successfulTransactionLabel());
      }
    });
    bankController.doPost(request,response);
  }
}