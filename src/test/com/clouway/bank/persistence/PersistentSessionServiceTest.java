package com.clouway.bank.persistence;

import com.clouway.bank.core.Account;
import com.clouway.bank.core.Clock;
import com.clouway.bank.core.User;
import com.google.inject.util.Providers;
import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public class PersistentSessionServiceTest {

  PersistentSessionService sessionService;
  PersistentAccountService accountService;
  User user;
  Account account;
  MysqlConnectionPoolDataSource dataSource;
  Connection connection;
  Clock clock;

  @Before
  public void setUp() throws Exception {
    account = new Account("5.00");
    user = new User("Torbalan", "unknown", account, "someId");
    clock = new Clock();
    dataSource = new MysqlConnectionPoolDataSource();

    dataSource.setUser("root");
    dataSource.setPassword("clouway.com");
    dataSource.setDatabaseName("bank");
    dataSource.setServerName("localhost");

    connection = dataSource.getConnection();
    sessionService = new PersistentSessionService(Providers.of(connection),clock);
    accountService = new PersistentAccountService(Providers.of(connection));

    sessionService.cleanSessionsTable();
    accountService.cleanAccountsTable();
    accountService.registerUser(user);
    sessionService.authenticate(user);
  }

  @Test
  public void getUserSessionIds() {
    User user2 = new User("Torbalan", "unknown", null, "someOtherId");
    sessionService.authenticate(user2);

    ArrayList<String>sessionIds = new ArrayList<String>();
    sessionIds.add(user.getSessionId());
    sessionIds.add(user2.getSessionId());

    assertThat(sessionService.getSessionIds(user.getUserName()), Matchers.<List<String>>is(sessionIds));
  }

  @Test
  public void findUserAssociatedWithSession() {
    assertThat(sessionService.findUserAssociatedWithSession("Torbalan"),is(user));
  }

}
