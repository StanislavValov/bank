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
public class LoginController extends HttpServlet {

  private AccountService accountService;
  private User user;

  @Inject
  public LoginController(AccountService accountService, User user) {
    this.accountService = accountService;
    this.user = user;
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    HttpSession session = req.getSession();
    user.setUserName(req.getParameter("userName"));
    user.setPassword(req.getParameter("password"));
//    Cookie userId = new Cookie("id",new Security().idGenerator());

    if (user.getPassword().equals(accountService.getPassword(user))) {
      session.setAttribute("userName", user.getUserName());
      req.getRequestDispatcher("/UserAccountController").forward(req, resp);
    } else {
      session.setAttribute("error", "Wrong Password or Username");
      resp.sendRedirect("/bank/Login.jsp");
    }
  }
}