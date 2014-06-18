package com.clouway.bank.http;

import com.clouway.bank.core.AccountService;
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
public class LoginController extends HttpServlet {

  private AccountService accountService;
  private Session session;
  private SiteMap siteMap;

  @Inject
  public LoginController(AccountService accountService, Session session, SiteMap siteMap) {
    this.accountService = accountService;
    this.session = session;
    this.siteMap = siteMap;
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String sessionId = session.getId();
    String password = req.getParameter(siteMap.password());
    String userName = req.getParameter(siteMap.userName());

    User user = new User(userName, password, null, sessionId);

    if (password.equals(accountService.getPassword(user))) {

      req.setAttribute(siteMap.userName(), userName);

      resp.addCookie(session.getCookie(userName, sessionId));

      accountService.addUserAssociatedWithSession(user);

      req.getRequestDispatcher(siteMap.userAccountController()).forward(req, resp);

    } else {
      req.setAttribute(siteMap.errorLabel(), siteMap.identificationFailed());
      req.getRequestDispatcher(siteMap.loginJspLabel()).include(req, resp);
    }
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    req.getRequestDispatcher(siteMap.userAccountController()).forward(req, resp);
  }
}