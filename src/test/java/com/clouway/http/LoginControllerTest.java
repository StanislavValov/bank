package com.clouway.http;

import com.clouway.core.*;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.servlet.http.Cookie;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public class LoginControllerTest {

    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    LoginController loginController;
    User user;
    Cookie cookie;
    FakeHttpServletResponse response;

    AuthorisationService authorisationService = context.mock(AuthorisationService.class);
    SessionRepository sessionRepository = context.mock(SessionRepository.class);
    SiteMap siteMap = context.mock(SiteMap.class);
    IdGenerator idIdGenerator = context.mock(IdGenerator.class);

    @Before
    public void setUp() throws Exception {
        user = new User();
        siteMap = new LabelMap();
        cookie = new Cookie("sid", "someId");
        loginController = new LoginController(authorisationService, sessionRepository, siteMap, idIdGenerator);
        loginController.setUser(user);
        response = new FakeHttpServletResponse() {
            @Override
            public void addCookie(Cookie cookie) {
                super.addCookie(cookie);
            }
        };
    }

    @Test
    public void loginSuccessFul() {

        user.setUserName("Torbalan");

        context.checking(new Expectations() {
            {
                oneOf(authorisationService).isAuthorised(user);
                will(returnValue(true));

                oneOf(idIdGenerator).generateFor(user);
                will(returnValue("123"));

                oneOf(sessionRepository).addUser(user, "123");
            }
        });
        assertThat(loginController.authorise(response), is("/bankController"));
    }

    @Test
    public void loginFailed() {

        context.checking(new Expectations() {
            {
                oneOf(authorisationService).isAuthorised(user);
                will(returnValue(false));
            }
        });
        assertThat(loginController.authorise(response),is("/bank/AuthenticationError.html"));
    }


}