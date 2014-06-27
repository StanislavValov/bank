package com.clouway.http;

import com.clouway.core.BankService;
import com.clouway.core.BankValidator;
import com.clouway.core.CurrentUser;
import com.clouway.core.SiteMap;
import com.clouway.core.User;
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

    String amount = req.getParameter(siteMap.transactionAmountLabel());

    User user = currentUserProvider.get().getUser();

    if (validator.isAmountValid(amount)) {

      if (req.getParameter(siteMap.depositLabel()) != null) {
        bankService.deposit(user, amount);
      }

      if (req.getParameter(siteMap.withdrawLabel()) != null) {
        bankService.withdraw(user, amount);
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