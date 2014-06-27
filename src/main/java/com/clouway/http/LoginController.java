package com.clouway.http;

import com.clouway.core.SiteMap;
import com.clouway.core.User;
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

  private AuthorisationService authorisationService;
  private Session session;
  private SiteMap siteMap;

  @Inject
  public LoginController(AuthorisationService authorisationService, Session session, SiteMap siteMap) {
    this.authorisationService = authorisationService;
    this.session = session;
    this.siteMap = siteMap;
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String password = req.getParameter(siteMap.password());
    String userName = req.getParameter(siteMap.userName());
    String sessionId = session.getId(userName, password);

    User user = new User(userName, password, sessionId);

    Cookie cookie = authorisationService.authenticate(user);

    if (cookie == null) {
      req.setAttribute(siteMap.errorLabel(), siteMap.identificationFailed());
      req.getRequestDispatcher(siteMap.loginJspLabel()).forward(req, resp);

    } else {
      req.setAttribute(siteMap.userName(), userName);
      resp.addCookie(cookie);
      req.getRequestDispatcher(siteMap.userAccountController()).forward(req, resp);
    }
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    req.getRequestDispatcher(siteMap.userAccountController()).forward(req, resp);
  }
}