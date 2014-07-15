package com.clouway.http;

import com.clouway.core.LabelMap;
import com.clouway.core.SiteMap;
import com.clouway.core.User;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public class LoginControllerTest {

  LoginController loginController;
  Mockery context = new JUnit4Mockery();
  User user;
  SiteMap siteMap;
  Cookie cookie;

  HttpServletRequest request = context.mock(HttpServletRequest.class);
  HttpServletResponse response = context.mock(HttpServletResponse.class);
  AuthorisationService authorisationService = context.mock(AuthorisationService.class);

  @Before
  public void setUp() throws Exception {
//    user = new User();
    siteMap = new LabelMap();
    cookie = new Cookie(user.getUserName(), user.getSessionId());
//    loginController = new LoginController(authorisationService, siteMap);
  }

  @Test
  public void loginSuccess() throws Exception {

    context.checking(new Expectations() {
      {
        oneOf(request).getParameter(siteMap.password());
        will(returnValue(user.getPassword()));

        oneOf(request).getParameter(siteMap.userName());
        will(returnValue(user.getUserName()));

        oneOf(authorisationService).isUserAuthorised(user);
        will(returnValue(cookie));

        oneOf(request).setAttribute(siteMap.userName(), user.getUserName());

        oneOf(response).addCookie(cookie);

        oneOf(request).getRequestDispatcher(siteMap.userAccountController());
      }
    });
  }

  @Test
  public void loginFailed() throws Exception {

    context.checking(new Expectations() {
      {
        oneOf(request).getParameter(siteMap.password());
        will(returnValue(user.getPassword()));

        oneOf(request).getParameter(siteMap.userName());
        will(returnValue(user.getUserName()));

        oneOf(authorisationService).isUserAuthorised(user);

        oneOf(request).setAttribute(siteMap.errorLabel(), siteMap.identificationFailed());

        oneOf(request).getRequestDispatcher(siteMap.loginJspLabel());
      }
    });
  }

  @Test
  public void successfulRedirect() throws Exception {

    context.checking(new Expectations() {
      {
        oneOf(request).getRequestDispatcher(siteMap.userAccountController());
      }
    });
  }
}