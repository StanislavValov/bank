package com.clouway.bank.http;

import com.clouway.bank.core.AccountService;
import com.clouway.bank.core.BankValidator;
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

  @Inject
  public RegistrationController(AccountService accountService, BankValidator validator) {
    this.accountService = accountService;
    this.validator = validator;
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    User user = new User();
    user.setUserName(req.getParameter("userName"));
    user.setPassword(req.getParameter("password"));

    if (validator.userNameIsValid(user.getUserName()) &&
            validator.passwordIsValid(user.getPassword())) {

      if (!accountService.userExists(user)) {
        accountService.registerUser(user);
        resp.sendRedirect("/bank/Login.jsp");
      } else {
        req.setAttribute("error", "Username already exist");
      }
    } else {
      req.setAttribute("error", "Please enter valid Username and password");
    }
    req.getRequestDispatcher("/bank/RegistrationForm.jsp").forward(req, resp);
  }
}
