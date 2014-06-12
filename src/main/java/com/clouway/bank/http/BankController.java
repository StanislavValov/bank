package com.clouway.bank.http;

import com.clouway.bank.core.Account;
import com.clouway.bank.core.BankService;
import com.clouway.bank.core.BankValidator;
import com.clouway.bank.core.CurrentUser;
import com.clouway.bank.core.User;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */

@Singleton
public class BankController extends HttpServlet {

  private BankService bankService;
  private BankValidator validator;
  private Provider<CurrentUser>currentUserProvider;

  @Inject
  public BankController(BankService bankService, BankValidator validator, Provider<CurrentUser> currentUserProvider) {
    this.bankService = bankService;
    this.validator = validator;
    this.currentUserProvider = currentUserProvider;
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    Account account = new Account(req.getParameter("amount"));
    User user = new User(currentUserProvider.get().getUserName(),null, account,null);

    if (validator.transactionIsValid(user)) {
      if ("Deposit".equals(req.getParameter("deposit"))) {
        bankService.deposit(user);
      }
      if ("Withdraw".equals(req.getParameter("withdraw"))) {
        bankService.withdraw(user);
      }
      resp.sendRedirect("/bank/SuccessfulTransaction.jsp");

    } else {
      req.setAttribute("invalidAmount", "Please enter valid amount");
      req.getRequestDispatcher("/bank/TransactionError.jsp").forward(req,resp);
    }
  }
}