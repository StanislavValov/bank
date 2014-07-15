package com.clouway.persistence;

import com.clouway.core.Clock;
import com.clouway.core.User;
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
public class PersistentBankServiceIT {

  PersistentAccountService accountService;
  PersistentBankService bankService;
  PersistentSessionService sessionService;
  User user;
  double amount;
  MysqlConnectionPoolDataSource dataSource;
  Connection connection;

  @Before
  public void setUp() throws Exception {
    amount = 5.00;
    user = new User(null,null);
    dataSource = new MysqlConnectionPoolDataSource();

    dataSource.setUser("root");
    dataSource.setPassword("clouway.com");
    dataSource.setDatabaseName("bank");
    dataSource.setServerName("localhost");

    connection = dataSource.getConnection();
    sessionService = new PersistentSessionService(Providers.of(connection), new Clock());
    bankService = new PersistentBankService(Providers.of(connection));
    accountService = new PersistentAccountService(Providers.of(connection));

    sessionService.cleanSessionsTable();
    accountService.cleanAccountsTable();
    accountService.registerUser(user);
  }

  @Test
  public void depositSuccessful() {
    bankService.deposit(user, amount);
    assertThat(accountService.getAccountAmount(user), is(5.0));
  }

  @Test
  public void withdrawSuccessful() {
    bankService.withdraw(user, amount);
    assertThat(accountService.getAccountAmount(user), is(0.0));
  }

  @Test
  public void withdrawMoreThenAvailableAmount() {
    bankService.withdraw(user, amount);
    assertThat(accountService.getAccountAmount(user), is(0.0));
  }
}