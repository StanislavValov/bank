//package com.clouway.http;
//
//import com.clouway.core.*;
//import com.clouway.core.SessionRepository;
//import com.google.inject.Provider;
//import org.jmock.Expectations;
//import org.jmock.integration.junit4.JUnitRuleMockery;
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.Date;
//
///**
// * Created by Stanislav Valov <hisazzul@gmail.com>
// */
//public class SecurityFilterTest {
//
//    @Rule
//    public JUnitRuleMockery context = new JUnitRuleMockery();
//
//    SecurityFilter securityFilter;
//    User user;
//    Session session;
//    Date date;
//
//    ServletRequest request = context.mock(ServletRequest.class);
//    HttpServletResponse response = context.mock(HttpServletResponse.class);
//    FilterChain filterChain = context.mock(FilterChain.class);
//    SessionRepository sessionRepository = context.mock(SessionRepository.class);
//    Provider provider = context.mock(Provider.class);
//    SiteMap siteMap = context.mock(SiteMap.class);
//
//    @Before
//    public void setUp() throws Exception {
//        user = new User("Stan");
//        securityFilter = new SecurityFilter(provider, sessionRepository, siteMap);
//    }
//
//    @Test
//    public void userIsNotFound() throws Exception {
//
//        context.checking(new Expectations() {
//            {
//                oneOf(provider).get();
//                will(returnValue(null));
//
//                oneOf(siteMap).loginForm();
//                will(returnValue("/bank/Login.html"));
//
//                oneOf(response).sendRedirect("/bank/Login.html");
//            }
//        });
//        securityFilter.doFilter(request, response, filterChain);
//    }
//
//    @Test
//    public void sessionWasExpired() throws Exception {
//        date = new Date(System.currentTimeMillis()-1000);
//        session = new Session("Stan","123",date);
//
//        context.checking(new Expectations() {
//            {
//                oneOf(provider).get();
//                will(returnValue(session));
//
//                oneOf(siteMap).logoutController();
//                will(returnValue("/logout"));
//
//                oneOf(response).sendRedirect("/logout");
//            }
//        });
//        securityFilter.doFilter(request, response, filterChain);
//    }
//
//    @Test
//    public void sessionWasReset() throws Exception {
//
//        date = CalendarUtil.sessionExpirationTime();
//        session = new Session("Stan","234",date);
//
//        context.checking(new Expectations() {
//            {
//                oneOf(provider).get();
//                will(returnValue(session));
//
//                oneOf(sessionRepository).reset(session.getId());
//
//                oneOf(filterChain).doFilter(request, response);
//            }
//        });
//        securityFilter.doFilter(request, response, filterChain);
//    }
//}