package com.clouway.http;

import com.clouway.core.*;
import com.clouway.core.SessionService;
import com.google.inject.Provider;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public class LogoutControllerTest {

  private LogoutController logoutController;
  private Cookie cookie;
  private Cookie[] cookies;
  private CurrentUser currentUser;
  private User user;

  Mockery context = new JUnit4Mockery();

  HttpServletRequest request = context.mock(HttpServletRequest.class);
  HttpServletResponse response = context.mock(HttpServletResponse.class);
  SessionService sessionService = context.mock(SessionService.class);
  Provider currentUserProvider = context.mock(Provider.class);
  SiteMap siteMap = context.mock(SiteMap.class);

  @Before
  public void setUp() throws Exception {
    logoutController = new LogoutController(currentUserProvider, sessionService, siteMap);
    user = new User("Stanislav","123456");
    cookie = new Cookie("Stanislav", "123456");
    cookies = new Cookie[]{cookie};
    currentUser = new CurrentUser(user);
  }

  @Test
  public void successfulLogout() throws IOException, ServletException {

    context.checking(new Expectations() {
      {
        oneOf(request).getCookies();
        will(returnValue(cookies));

        oneOf(currentUserProvider).get();
        will(returnValue(currentUser));

        oneOf(sessionService).removeSession(user.getSessionId());

        oneOf(response).addCookie(cookie);

        oneOf(siteMap).loginForm();
        will(returnValue("/bank/Login.html"));
      }
    });
      logoutController.logout(request,response);
  }
}