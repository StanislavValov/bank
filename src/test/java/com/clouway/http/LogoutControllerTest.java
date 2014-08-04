package com.clouway.http;

import com.clouway.core.Session;
import com.clouway.core.SessionService;
import com.clouway.core.SiteMap;
import com.clouway.core.User;
import com.google.inject.Provider;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Date;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public class LogoutControllerTest {

    private LogoutController logoutController;
    private Session session;

    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    SessionService sessionService = context.mock(SessionService.class);
    Provider currentUserProvider = context.mock(Provider.class);
    SiteMap siteMap = context.mock(SiteMap.class);

    @Before
    public void setUp() throws Exception {
        logoutController = new LogoutController(currentUserProvider, sessionService, siteMap);
        session = new Session("Stanislav","123",new Date());
    }

    @Test
    public void successfulLogout() throws IOException, ServletException {

        context.checking(new Expectations() {
            {
                oneOf(currentUserProvider).get();
                will(returnValue(session));

                oneOf(sessionService).remove(session.getId());

                oneOf(siteMap).loginForm();
                will(returnValue("/bank/Login.html"));
            }
        });
        logoutController.logout();
    }
}