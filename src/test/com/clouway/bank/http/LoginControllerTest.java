package com.clouway.bank.http;

import com.clouway.bank.core.AccountService;
import com.clouway.bank.core.LabelMap;
import com.clouway.bank.core.SiteMap;
import com.clouway.bank.core.User;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

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
  AccountService accountService = context.mock(AccountService.class);
  Session session = context.mock(Session.class);


  @Before
  public void setUp() throws Exception {
    user = new User("Torbalan", "unknown", null, "123");
    siteMap = new LabelMap();
    cookie = new Cookie(user.getUserName(),user.getSessionId());
    loginController = new LoginController(accountService, session, siteMap);
  }


  @Test
  public void equality() throws Exception {
    assertThat(user,is((new User("Torbalan","unknown",null, "123"))));
  }

  @Test
  public void objectsAreNotEqual() throws Exception {
    assertThat(user,is(not(new User("Tor", "known", null, "111"))));
  }

  @Test
  public void loginSuccess() throws Exception {

    context.checking(new Expectations() {
      {
        oneOf(session).getId();
        will(returnValue(user.getSessionId()));

        oneOf(request).getParameter(siteMap.password());
        will(returnValue(user.getPassword()));

        oneOf(request).getParameter(siteMap.userName());
        will(returnValue(user.getUserName()));

        oneOf(accountService).getPassword(user);
        will(returnValue(user.getPassword()));

        oneOf(request).setAttribute(siteMap.userName(),user.getUserName());

        oneOf(session).getCookie(user.getUserName(),user.getSessionId());
        will(returnValue(cookie));

        oneOf(response).addCookie(cookie);

        oneOf(accountService).addUserAssociatedWithSession(user);

        oneOf(request).getRequestDispatcher(siteMap.userAccountController());
      }
    });
    loginController.doPost(request, response);
  }

  @Test
  public void loginFailed() throws Exception {

    context.checking(new Expectations() {
      {
        oneOf(session).getId();
        will(returnValue(user.getSessionId()));

        oneOf(request).getParameter(siteMap.password());
        will(returnValue(user.getPassword()));

        oneOf(request).getParameter(siteMap.userName());
        will(returnValue(user.getUserName()));

        oneOf(accountService).getPassword(user);
        will(returnValue("1122"));

        oneOf(request).setAttribute(siteMap.errorLabel(),siteMap.identificationFailed());

        oneOf(request).getRequestDispatcher(siteMap.loginJspLabel());
      }
    });
    loginController.doPost(request, response);
  }

  @Test
  public void successfulRedirectToUAC() throws Exception {

    context.checking(new Expectations(){
      {
        oneOf(request).getRequestDispatcher(siteMap.userAccountController());
      }
    });
    loginController.doGet(request,response);
  }
}