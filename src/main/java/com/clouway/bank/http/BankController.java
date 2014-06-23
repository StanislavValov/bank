package com.clouway.bank.http;

import com.clouway.bank.core.Account;
import com.clouway.bank.core.BankService;
import com.clouway.bank.core.BankValidator;
import com.clouway.bank.core.CurrentUser;
import com.clouway.bank.core.SiteMap;
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
  private Provider<CurrentUser> currentUserProvider;
  private SiteMap siteMap;

  @Inject
  public BankController(BankService bankService, BankValidator validator, Provider<CurrentUser> currentUserProvider, SiteMap siteMap) {
    this.bankService = bankService;
    this.validator = validator;
    this.currentUserProvider = currentUserProvider;
    this.siteMap = siteMap;
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    Account account = new Account(req.getParameter(siteMap.amountLabel()));

    User user = new User(currentUserProvider.get().getUser().getUserName(), account);

    if (validator.transactionIsValid(user)) {

      if (req.getParameter(siteMap.depositLabel()) != null) {
        bankService.deposit(user);
      }

      if (req.getParameter(siteMap.withdrawLabel()) != null) {
        bankService.withdraw(user);
      }

      resp.sendRedirect(siteMap.successfulTransactionLabel());

    } else {
      req.setAttribute(siteMap.amountErrorLabel(), siteMap.amountErrorMessage());
      req.getRequestDispatcher(siteMap.transactionErrorLabel()).forward(req, resp);
    }
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.sendRedirect(siteMap.userAccountController());
  }
}