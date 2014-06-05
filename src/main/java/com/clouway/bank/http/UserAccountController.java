package com.clouway.bank.http;

import com.clouway.bank.core.AccountService;
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
public class UserAccountController extends HttpServlet {

  private AccountService accountService;
  private User user;

  @Inject
  public UserAccountController(AccountService accountService, User user) {
    this.accountService = accountService;
    this.user = user;
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    HttpSession session = req.getSession();
    user.setUserName((String) session.getAttribute("userName"));

    session.setAttribute("amount", accountService.getAccountAmount(user));
    resp.sendRedirect("/bank/User.jsp");
  }
}
