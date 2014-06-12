package com.clouway.bank.http;

import com.clouway.bank.core.AccountService;
import com.clouway.bank.core.Security;
import com.clouway.bank.core.TimeUtility;
import com.clouway.bank.core.User;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
@Singleton
public class LoginController extends HttpServlet {

  private AccountService accountService;
  private Security security;

  @Inject
  public LoginController(AccountService accountService, Security security, TimeUtility timeUtility) {
    this.accountService = accountService;
    this.security = security;
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    String sessionId = req.getParameter("userName");
    User user = new User(req.getParameter("userName"),
            req.getParameter("password"), null, sessionId);

    if (user.getPassword().equals(accountService.getPassword(user))) {

      req.setAttribute("userName",user.getUserName());

      Cookie sessionCookie = new Cookie(req.getParameter("userName"),sessionId);
      resp.addCookie(sessionCookie);
      if (accountService.findUserAssociatedWithSession(sessionCookie.getValue())==null) {
        accountService.addUserAssociatedWithSession(user);
      }

      req.getRequestDispatcher("/UserAccountController.do").forward(req, resp);
    } else {
      req.setAttribute("error", "Wrong Password or Username");
      req.getRequestDispatcher("/bank/Login.jsp").forward(req,resp);
    }

  }
}