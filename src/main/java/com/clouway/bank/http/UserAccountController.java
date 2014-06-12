package com.clouway.bank.http;

import com.clouway.bank.core.AccountService;
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
public class UserAccountController extends HttpServlet {

  private AccountService accountService;

  @Inject
  public UserAccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    req.setAttribute("amount", accountService.getAccountAmount((String) req.getAttribute("userName")));
    req.getRequestDispatcher("/bank/User.jsp").include(req, resp);
//    resp.sendRedirect("/bank/User.jsp");
  }
}