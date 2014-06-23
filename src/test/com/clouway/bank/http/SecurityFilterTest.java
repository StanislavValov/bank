package com.clouway.bank.http;

import com.clouway.bank.core.ClockUtil;
import com.clouway.bank.core.CurrentUser;
import com.clouway.bank.core.LabelMap;
import com.clouway.bank.core.SiteMap;
import com.clouway.bank.core.User;
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

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public class SecurityFilterTest {

  Mockery context = new JUnit4Mockery();
  SecurityFilter securityFilter;
  SiteMap siteMap;
  User user;
  CurrentUser currentUser;

  ServletRequest request = context.mock(ServletRequest.class);
  HttpServletResponse response = context.mock(HttpServletResponse.class);
  FilterChain filterChain = context.mock(FilterChain.class);
  SessionService sessionService = context.mock(SessionService.class);
  Provider provider = context.mock(Provider.class);
  ClockUtil clockUtil = context.mock(ClockUtil.class);

  @Before
  public void setUp() throws Exception {
    user = new User("Torbalan", "unknown", null, "111");
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
  public void sessionExpired() throws Exception {

    final Timestamp expirationTIme = new Timestamp(System.currentTimeMillis() - 5 * 60 * 1000);
    final Timestamp currentTime = new Timestamp(System.currentTimeMillis());

    context.checking(new Expectations() {
      {
        oneOf(provider).get();
        will(returnValue(currentUser));

        oneOf(sessionService).getSessionExpirationTime(user);
        will(returnValue(expirationTIme));

        oneOf(clockUtil).currentTime();
        will(returnValue(currentTime));

        oneOf(sessionService).removeSessionId(user);

        oneOf(response).sendRedirect(siteMap.loginJspLabel());
      }
    });
    securityFilter.doFilter(request, response, filterChain);
  }

  @Test
  public void sessionIsActive() throws Exception {
    final Timestamp expirationTIme = new Timestamp(System.currentTimeMillis() + 5 * 60 * 1000);
    final Timestamp currentTime = new Timestamp(System.currentTimeMillis());

    context.checking(new Expectations() {
      {
        oneOf(provider).get();
        will(returnValue(currentUser));

        oneOf(sessionService).getSessionExpirationTime(user);
        will(returnValue(expirationTIme));

        oneOf(clockUtil).currentTime();
        will(returnValue(currentTime));

        oneOf(sessionService).resetSessionLife(user);

        oneOf(filterChain).doFilter(request, response);
      }
    });
    securityFilter.doFilter(request, response, filterChain);
  }
}