package com.clouway.bank.http;

import com.clouway.bank.core.Account;
import com.clouway.bank.core.AccountService;
import com.clouway.bank.core.BankService;
import com.clouway.bank.core.BankValidator;
import com.clouway.bank.core.User;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */

@Singleton
public class BankController extends HttpServlet {

  private BankService bankService;
  private AccountService accountService;
  private BankValidator validator;
  private User user;

  @Inject
  public BankController(BankService bankService, AccountService accountService, BankValidator validator, User user) {
    this.bankService = bankService;
    this.accountService = accountService;
    this.validator = validator;
    this.user = user;
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    HttpSession session = req.getSession();
    Account account = new Account();
    account.setTransactionAmount(req.getParameter("amount"));
    user.setUserName((String) session.getAttribute("userName"));
    user.setAccount(account);

    if (validator.transactionIsValid(user)) {
      if ("Deposit".equals(req.getParameter("deposit"))) {
        bankService.deposit(user);
        resp.sendRedirect("/bank/User.jsp");
      }
      if ("Withdraw".equals(req.getParameter("withdraw"))) {
        bankService.withdraw(user);
        resp.sendRedirect("/bank/User.jsp");
      }
      session.setAttribute("amount", accountService.getAccountAmount(user));

    } else {
      session.setAttribute("invalidAmount", "Please enter valid amount");
      resp.sendRedirect("/bank/TransactionError.jsp");
    }
  }
}