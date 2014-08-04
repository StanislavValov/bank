package com.clouway.http;

import com.clouway.core.*;
import com.clouway.core.SessionService;
import com.google.inject.Provider;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public class SecurityFilterTest {

    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    SecurityFilter securityFilter;
    User user;
    Session session;
    Map<String, Timestamp> sessionsExpirationTime;

    ServletRequest request = context.mock(ServletRequest.class);
    HttpServletResponse response = context.mock(HttpServletResponse.class);
    FilterChain filterChain = context.mock(FilterChain.class);
    SessionService sessionService = context.mock(SessionService.class);
    Provider provider = context.mock(Provider.class);
    SiteMap siteMap = context.mock(SiteMap.class);

    @Before
    public void setUp() throws Exception {
        sessionsExpirationTime = new HashMap<String, Timestamp>();
        user = new User();
        session = new Session("Stan","123",new Date());
        securityFilter = new SecurityFilter(provider, sessionService, siteMap);
    }

    @Test
    public void userIsNotFound() throws Exception {

        context.checking(new Expectations() {
            {
                oneOf(provider).get();
                will(returnValue(null));

                oneOf(sessionService).get("123");

                oneOf(siteMap).loginForm();
                will(returnValue("/bank/Login.html"));

                oneOf(response).sendRedirect("/bank/Login.html");
            }
        });
        securityFilter.doFilter(request, response, filterChain);
    }

    @Test
    public void sessionWasExpired() throws Exception {

        final Timestamp expirationTime = new Timestamp(System.currentTimeMillis() - 5 * 60 * 1000);
        final Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        sessionsExpirationTime.put(session.getId(), expirationTime);

        context.checking(new Expectations() {
            {
                oneOf(provider).get();
                will(returnValue(user));

                oneOf(sessionService).get("123");
                will(returnValue(sessionsExpirationTime));

                oneOf(sessionService).remove(session.getId());

                oneOf(siteMap).logoutController();
                will(returnValue("/logout"));

                oneOf(response).sendRedirect("/logout");
            }
        });
        securityFilter.doFilter(request, response, filterChain);
    }

    @Test
    public void sessionWasReset() throws Exception {

        final Timestamp expirationTIme = new Timestamp(System.currentTimeMillis() + 5 * 60 * 1000);
        final Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        sessionsExpirationTime.put(session.getId(), expirationTIme);

        context.checking(new Expectations() {
            {
                oneOf(provider).get();
                will(returnValue(user));

                oneOf(sessionService).get("123");
                will(returnValue(sessionsExpirationTime));

                oneOf(sessionService).reset(session.getId());

                oneOf(filterChain).doFilter(request, response);
            }
        });
        securityFilter.doFilter(request, response, filterChain);
    }

    @Test
    public void expiredSessionWasDeleted() throws Exception {

        final Timestamp expirationTIme = new Timestamp(System.currentTimeMillis() - 1);
        final Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        sessionsExpirationTime.put(session.getId(), expirationTIme);

        context.checking(new Expectations() {
            {
                oneOf(provider).get();
                will(returnValue(user));

                oneOf(sessionService).get("123");
                will(returnValue(sessionsExpirationTime));

                oneOf(sessionService).reset(null);

                oneOf(siteMap).logoutController();
                will(returnValue("/logout"));

                oneOf(filterChain).doFilter(request,response);

                oneOf(sessionService).remove(session.getId());
            }
        });
        securityFilter.doFilter(request, response, filterChain);
    }
}