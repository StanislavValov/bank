package com.clouway.persistence;

import com.clouway.core.ClockUtil;
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
public class PersistentSessionServiceIT {

    PersistentSessionService sessionService;
    PersistentAccountService accountService;
    User user;
    ClockUtil clockUtil;

    @Before
    public void setUp() throws Exception {
        user = new User("Thor", "someId");

        clockUtil = new ClockUtil() {

            @Override
            public Date currentTime() {
                return CalendarUtil.june(2014, 5);
            }

            @Override
            public Date expirationDate() {
                return CalendarUtil.june(2014, 6);
            }
        };

        sessionService = new PersistentSessionService(clockUtil);
        accountService = new PersistentAccountService();

        sessionService.cleanSessionsTable();
        accountService.deleteUser(user);
        accountService.registerUser(user);
        sessionService.addUserAssociatedWithSession(user, user.getSessionId());
    }

    @Test
    public void findUserAssociatedWithSession() {
        assertThat(sessionService.findUserAssociatedWithSession("someId"), is(user));
    }

    @Test
    public void getSessionsExpirationTime() {
        assertThat(sessionService.getSessionsExpirationTime().get(user.getSessionId()), is(CalendarUtil.june(2014, 6)));
    }

    @Test
    public void sessionLifeWasReset() {
        sessionService.resetSessionLife(user.getSessionId());
        assertThat(sessionService.getSessionsExpirationTime().get(user.getSessionId()), is(CalendarUtil.june(2014, 6)));
    }

    @Test
    public void removeSessionIdSuccessful() {
        sessionService.removeSession(user.getSessionId());
        assertNull(sessionService.findUserAssociatedWithSession(user.getSessionId()));
    }

    @Test
    public void getSessionsCount() {
        assertThat(sessionService.getSessionsCount(), is(1));
    }
}