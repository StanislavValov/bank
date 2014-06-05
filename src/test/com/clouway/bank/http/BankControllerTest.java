package com.clouway.bank.http;

import com.clouway.bank.core.Account;
import com.clouway.bank.core.AccountService;
import com.clouway.bank.core.BankService;
import com.clouway.bank.core.BankValidator;
import com.clouway.bank.core.User;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public class BankControllerTest {

  BankController bankController = null;
  Mockery context = new JUnit4Mockery();
  Account account;
  User user;

  HttpServletRequest request = context.mock(HttpServletRequest.class);
  HttpServletResponse response = context.mock(HttpServletResponse.class);
  HttpSession session = context.mock(HttpSession.class);
  BankService bankService = context.mock(BankService.class);
  AccountService accountService = context.mock(AccountService.class);
  BankValidator validator = context.mock(BankValidator.class);

  @Before
  public void setUp() throws Exception {
    user = new User();
    account = new Account();
    bankController = new BankController(bankService, accountService, validator, user);
  }

  @Test
  public void amountIsNotCorrect() throws Exception {

    account.setTransactionAmount("1.11");
    user.setUserName("Ivan");
    user.setAccount(account);

    context.checking(new Expectations() {
      {
        oneOf(request).getSession();
        will(returnValue(session));

        oneOf(request).getParameter("amount");
        will(returnValue("1.12"));

        oneOf(validator).transactionIsValid(user);
        will(returnValue(false));

        oneOf(session).setAttribute("invalidAmount", "Please enter valid amount");
        oneOf(request).getParameter("withdraw");

        oneOf(session).getAttribute("userName");
        will(returnValue(user.getUserName()));

        oneOf(response).sendRedirect("/bank/TransactionError.jsp");
      }
    });
    bankController.doPost(request, response);
  }

  @Test
  public void depositSuccess() throws Exception {
    user.setUserName("Ivan");
    context.checking(new Expectations() {
      {
        oneOf(request).getSession();
        will(returnValue(session));

        oneOf(request).getParameter("amount");
        will(returnValue("1.12"));

        oneOf(validator).transactionIsValid(user);
        will(returnValue(true));

        oneOf(request).getParameter("deposit");
        will(returnValue("Deposit"));

        oneOf(bankService).deposit(user);

        oneOf(request).getParameter("amount");
        will(returnValue("1.12"));

        oneOf(response).sendRedirect("/bank/User.jsp");
        oneOf(request).getParameter("withdraw");

        oneOf(accountService).getAccountAmount(user);
        will(returnValue(1.12));

        oneOf(session).setAttribute("amount", 1.12);
        oneOf(session).getAttribute("userName");
        will(returnValue(user.getUserName()));
      }
    });
    bankController.doPost(request, response);
  }
}
