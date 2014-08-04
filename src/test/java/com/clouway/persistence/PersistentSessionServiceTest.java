package com.clouway.persistence;

import com.clouway.core.CalendarUtil;
import com.clouway.core.Session;
import com.clouway.core.User;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 * Created by hisazzul@gmail.com on 7/16/14.
 */
public class PersistentSessionServiceTest {

    PersistentSessionService sessionService;
    PersistentUserRepository accountService;
    User user;
    Session session;

    @Before
    public void setUp() throws Exception {
        user = new User("Thor");
        session = new Session("Thor","someId",new Date());

        sessionService = new PersistentSessionService();
        accountService = new PersistentUserRepository();

        sessionService.cleanSessionsTable();
        accountService.delete(user.getUserName());
        accountService.register(user);
        sessionService.addUser(user, session.getId());
    }

    @Test
    public void findUserAssociatedWithSession() {
        assertThat(sessionService.get("someId"), is(session));
    }

    @Test
    public void getSessionsExpirationTime() {
        assertThat(sessionService.get("someId").getExpirationDate(), is(CalendarUtil.sessionExpirationTime()));
    }

    @Test
    public void sessionLifeWasReset() {
        sessionService.reset(session.getId());
        assertThat(sessionService.get("someId").getExpirationDate(), is(CalendarUtil.sessionExpirationTime()));
    }

    @Test
    public void removeSessionIdSuccessful() {
        sessionService.remove(session.getId());
        assertNull(sessionService.get(session.getId()));
    }
}