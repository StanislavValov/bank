package com.clouway.http;

import com.clouway.core.ClockUtil;
import com.clouway.core.CurrentUser;
import com.clouway.core.LabelMap;
import com.clouway.core.SiteMap;
import com.clouway.core.User;
import com.google.inject.Provider;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public class SecurityFilterTest {

  Mockery context = new JUnit4Mockery();
  SecurityFilter securityFilter;
  SiteMap siteMap;
  User user;
  CurrentUser currentUser;
  Map<String, Timestamp> sessionsExpirationTime;

  ServletRequest request = context.mock(ServletRequest.class);
  HttpServletResponse response = context.mock(HttpServletResponse.class);
  FilterChain filterChain = context.mock(FilterChain.class);
  SessionService sessionService = context.mock(SessionService.class);
  Provider provider = context.mock(Provider.class);
  ClockUtil clockUtil = context.mock(ClockUtil.class);

  @Before
  public void setUp() throws Exception {
    sessionsExpirationTime = new HashMap<String, Timestamp>();
    user = new User("Torbalan", "unknown", "111");
    currentUser = new CurrentUser(user);
    siteMap = new LabelMap();
    securityFilter = new SecurityFilter(provider, sessionService, siteMap, clockUtil);
  }

  @Test
  public void userIsNotFound() throws Exception {

    context.checking(new Expectations() {
      {
        oneOf(provider).get();
        will(returnValue(new CurrentUser(null)));

        oneOf(response).sendRedirect(siteMap.loginJspLabel());
      }
    });
    securityFilter.doFilter(request, response, filterChain);
  }

  @Test
  public void sessionWasExpired() throws Exception {

    final Timestamp expirationTime = new Timestamp(System.currentTimeMillis() - 5 * 60 * 1000);
    final Timestamp currentTime = new Timestamp(System.currentTimeMillis());
    sessionsExpirationTime.put(user.getSessionId(), expirationTime);

    context.checking(new Expectations() {
      {
        oneOf(provider).get();
        will(returnValue(currentUser));

        oneOf(sessionService).getSessionsExpirationTime();
        will(returnValue(sessionsExpirationTime));

        oneOf(clockUtil).currentTime();
        will(returnValue(currentTime));

        oneOf(sessionService).removeSessionId(user.getSessionId());

        oneOf(request).getRequestDispatcher(siteMap.logoutController());
      }
    });
    securityFilter.doFilter(request, response, filterChain);
  }

  @Test
  public void sessionWasReset() throws Exception {

    final Timestamp expirationTIme = new Timestamp(System.currentTimeMillis() + 5 * 60 * 1000);
    final Timestamp currentTime = new Timestamp(System.currentTimeMillis());
    sessionsExpirationTime.put(user.getSessionId(), expirationTIme);

    context.checking(new Expectations() {
      {
        oneOf(provider).get();
        will(returnValue(currentUser));

        oneOf(sessionService).getSessionsExpirationTime();
        will(returnValue(sessionsExpirationTime));

        oneOf(clockUtil).currentTime();
        will(returnValue(currentTime));

        oneOf(sessionService).resetSessionLife(user.getSessionId());

        oneOf(filterChain).doFilter(request, response);
      }
    });
    securityFilter.doFilter(request, response, filterChain);
  }

  @Test
  public void expiredSessionWasDeleted() throws Exception {

    final Timestamp expirationTIme = new Timestamp(System.currentTimeMillis() - 1);
    final Timestamp currentTime = new Timestamp(System.currentTimeMillis());
    sessionsExpirationTime.put(user.getSessionId(), expirationTIme);

    context.checking(new Expectations() {
      {
        oneOf(provider).get();
        will(returnValue(currentUser));

        oneOf(sessionService).getSessionsExpirationTime();
        will(returnValue(sessionsExpirationTime));

        oneOf(clockUtil).currentTime();
        will(returnValue(currentTime));

        oneOf(request).getRequestDispatcher(siteMap.logoutController());

        oneOf(sessionService).removeSessionId(user.getSessionId());
      }
    });
    securityFilter.doFilter(request, response, filterChain);
  }
}