package com.clouway.bank.http;

import com.clouway.bank.core.AccountService;
import com.clouway.bank.core.BankValidator;
import com.clouway.bank.core.SiteMap;
import com.clouway.bank.core.User;
import com.google.inject.Inject;
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
public class RegistrationController extends HttpServlet {

  private AccountService accountService;
  private BankValidator validator;
  private SiteMap siteMap;

  @Inject
  public RegistrationController(AccountService accountService, BankValidator validator, SiteMap siteMap) {
    this.accountService = accountService;
    this.validator = validator;
    this.siteMap = siteMap;
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    String userName = req.getParameter(siteMap.userName());
    String password = req.getParameter(siteMap.password());

    User user = new User(userName, password);

    if (validator.isUserCorrect(user)) {

      if (!accountService.userExists(user)) {
        accountService.registerUser(user);
        resp.sendRedirect(siteMap.loginJspLabel());

      } else {
        req.setAttribute(siteMap.errorLabel(), siteMap.userExistErrorLabel());
      }

    } else {
      req.setAttribute(siteMap.errorLabel(), siteMap.validateErrorMessage());
    }
    req.getRequestDispatcher(siteMap.registrationJspLabel()).forward(req, resp);
  }
}