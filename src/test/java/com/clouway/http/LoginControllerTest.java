package com.clouway.http;

import com.clouway.core.*;
import com.clouway.persistence.FakeHttpServletResponse;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.Cookie;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public class LoginControllerTest {

    Mockery context = new JUnit4Mockery();
    LoginController loginController;
    User user;
    Cookie cookie;
    FakeHttpServletResponse response;

    AuthorisationService authorisationService = context.mock(AuthorisationService.class);
    SessionService sessionService = context.mock(SessionService.class);
    SiteMap siteMap = context.mock(SiteMap.class);
    Generator idGenerator = context.mock(Generator.class);

    @Before
    public void setUp() throws Exception {
        user = new User();
        siteMap = new LabelMap();
        cookie = new Cookie("sid", "someId");
        loginController = new LoginController(authorisationService, sessionService, siteMap, idGenerator);
        loginController.setUser(user);
        response = new FakeHttpServletResponse() {
            @Override
            public void addCookie(Cookie cookie) {
                super.addCookie(cookie);
            }
        };
    }

    @Test
    public void loginSuccessFul()  {

        user.setUserName("Torbalan");

        context.checking(new Expectations() {
            {
                oneOf(authorisationService).isUserAuthorised(user);
                will(returnValue(true));

                oneOf(idGenerator).getUniqueId(user);
                will(returnValue("123"));

                oneOf(sessionService).addUserAssociatedWithSession(user, "123");
            }
        });
        assertThat(loginController.authorise(response), is("/bankController"));
    }

    @Test
    public void loginFailed()  {

        context.checking(new Expectations() {
            {
                oneOf(authorisationService).isUserAuthorised(user);
                will(returnValue(false));

                oneOf(idGenerator).getUniqueId(user);
                will(returnValue("123"));
            }
        });
        assertNull(loginController.authorise(response));
    }
}