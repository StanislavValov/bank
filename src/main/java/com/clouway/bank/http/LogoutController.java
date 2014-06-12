package com.clouway.bank.http;

import com.clouway.bank.core.AccountService;
import com.clouway.bank.core.CurrentUser;
import com.google.inject.Inject;
import com.google.inject.Provider;
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
public class LogoutController extends HttpServlet {

  private Provider<CurrentUser>currentUserProvider;
  private AccountService accountService;

  @Inject
  public LogoutController(Provider<CurrentUser> currentUserProvider, AccountService accountService) {
    this.currentUserProvider = currentUserProvider;
    this.accountService = accountService;
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    for (Cookie cookie : req.getCookies()){
      if (cookie.getName().equalsIgnoreCase(currentUserProvider.get().getUserName())){
        cookie.setMaxAge(0);
        accountService.removeSessionId(currentUserProvider.get().getUserName());
        resp.addCookie(cookie);
      }
    }
    resp.sendRedirect("/bank/Login.jsp");
  }
}
