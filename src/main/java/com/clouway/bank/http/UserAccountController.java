package com.clouway.bank.http;

import com.clouway.bank.core.AccountService;
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
public class UserAccountController extends HttpServlet {

  private AccountService accountService;
  private SiteMap siteMap;
  private Provider<CurrentUser>currentUserProvider;

  @Inject
  public UserAccountController(AccountService accountService, SiteMap siteMap, Provider<CurrentUser> currentUserProvider) {
    this.accountService = accountService;
    this.siteMap = siteMap;
    this.currentUserProvider = currentUserProvider;
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String userName = (String) req.getAttribute(siteMap.userName());
    User user = new User(userName);

    req.setAttribute(siteMap.amountLabel(), accountService.getAccountAmount(user));
    req.getRequestDispatcher(siteMap.userJspLabel()).include(req, resp);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    req.setAttribute(siteMap.amountLabel(), accountService.getAccountAmount(currentUserProvider.get().getUser()));
    req.getRequestDispatcher(siteMap.userJspLabel()).include(req, resp);
  }
}