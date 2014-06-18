package com.clouway.bank.http;

import com.clouway.bank.core.AccountService;
import com.clouway.bank.core.CurrentUser;
import com.clouway.bank.core.SiteMap;
import com.clouway.bank.core.User;
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

  private Provider<CurrentUser> currentUserProvider;
  private AccountService accountService;
  private SiteMap siteMap;

  @Inject
  public LogoutController(Provider<CurrentUser> currentUserProvider, AccountService accountService, SiteMap siteMap) {
    this.currentUserProvider = currentUserProvider;
    this.accountService = accountService;
    this.siteMap = siteMap;
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    Cookie[] cookies = req.getCookies();

    for (Cookie cookie : cookies) {

      User currentUser = currentUserProvider.get().getUser();
      if (currentUser.getSessionId().equalsIgnoreCase(cookie.getValue())) {
        cookie.setMaxAge(0);
        accountService.removeSessionId(currentUser);
        resp.addCookie(cookie);
      }
    }
    resp.sendRedirect(siteMap.loginJspLabel());
  }
}