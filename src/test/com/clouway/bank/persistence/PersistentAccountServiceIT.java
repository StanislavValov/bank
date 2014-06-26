package com.clouway.bank.persistence;

import com.clouway.bank.core.Clock;
import com.clouway.bank.core.User;
import com.google.inject.util.Providers;
import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public class PersistentAccountServiceIT {

  PersistentAccountService accountService;
  PersistentSessionService sessionService;
  User user;
  MysqlConnectionPoolDataSource dataSource;
  Connection connection;

  @Before
  public void setUp() throws Exception {
    user = new User("Ivan", "unknown", "someId");
    dataSource = new MysqlConnectionPoolDataSource();

    dataSource.setUser("root");
    dataSource.setPassword("clouway.com");
    dataSource.setDatabaseName("bank");
    dataSource.setServerName("localhost");

    connection = dataSource.getConnection();
    accountService = new PersistentAccountService(Providers.of(connection));
    sessionService = new PersistentSessionService(Providers.of(connection),new Clock());

    sessionService.cleanSessionsTable();
    accountService.cleanAccountsTable();
    accountService.registerUser(user);
  }

  @Test
  public void userExist() {
    assertThat(accountService.userExists(user), is(true));
  }

  @Test
  public void userNotExist() {
    assertThat(accountService.userExists(new User("Hulk")), is(false));
  }

  @Test
  public void getAmount() {
    assertThat(accountService.getAccountAmount(user),is(0.0));
  }
}