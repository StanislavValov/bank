package com.clouway.bank.persistence;

import com.clouway.bank.core.ClockUtil;
import com.clouway.bank.core.User;
import com.google.inject.util.Providers;
import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Timestamp;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public class PersistentSessionServiceIT {

  PersistentSessionService sessionService;
  PersistentAccountService accountService;
  User user;
  MysqlConnectionPoolDataSource dataSource;
  Connection connection;
  ClockUtil clockUtil;

  @Before
  public void setUp() throws Exception {
    user = new User("Thor", "unknown", "someId");
    dataSource = new MysqlConnectionPoolDataSource();

    dataSource.setUser("root");
    dataSource.setPassword("clouway.com");
    dataSource.setDatabaseName("bank");
    dataSource.setServerName("localhost");

    clockUtil = new ClockUtil() {
      @Override
      public Timestamp currentTime() {
        return null;
      }

      @Override
      public Timestamp expirationDate() {
        return CalendarUtil.june(2014,5);
      }
    };

    connection = dataSource.getConnection();
    sessionService = new PersistentSessionService(Providers.of(connection),clockUtil);
    accountService = new PersistentAccountService(Providers.of(connection));

    sessionService.cleanSessionsTable();
    accountService.cleanAccountsTable();
    accountService.registerUser(user);
    sessionService.authenticate(user);
  }

  @Test
  public void findUserAssociatedWithSession() {
    assertThat(sessionService.findUserAssociatedWithSession("someId"),is(user));
  }

  @Test
  public void getSessionsExpirationTime() {
    assertThat(sessionService.getSessionsExpirationTime().get(user.getSessionId()),is(CalendarUtil.june(2014,5)));
  }

  @Test
  public void sessionLifeWasReset() {
    sessionService.resetSessionLife(user.getSessionId());
    assertThat(sessionService.getSessionsExpirationTime().get(user.getSessionId()),is(CalendarUtil.june(2014,5)));
  }

  @Test
  public void removeSessionIdSuccessful() {
    sessionService.removeSessionId(user.getSessionId());
    assertNull(sessionService.findUserAssociatedWithSession(user.getSessionId()));
  }

  @Test
  public void getSessionsCount() {
    assertThat(sessionService.getSessionsCount(),is(1));
  }
}