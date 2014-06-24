package com.clouway.bank.persistence;

import com.clouway.bank.core.Account;
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
public class PersistentAccountServiceTest {

  PersistentAccountService accountService;
  User user;
  Account account;
  MysqlConnectionPoolDataSource dataSource;
  Connection connection;

  @Before
  public void setUp() throws Exception {
    account = new Account("5.00");
    user = new User("Torbalan", "unknown", account, "someId");
    dataSource = new MysqlConnectionPoolDataSource();

    dataSource.setUser("root");
    dataSource.setPassword("clouway.com");
    dataSource.setDatabaseName("bank");
    dataSource.setServerName("localhost");

    connection = dataSource.getConnection();
    accountService = new PersistentAccountService(Providers.of(connection));

    accountService.cleanAccountsTable();
    accountService.registerUser(user);
  }

  @Test
  public void userExist() {
    assertThat(accountService.userExists(user), is(true));
  }

  @Test
  public void userNotExist() {
    assertThat(accountService.userExists(new User("Ivan")), is(false));
  }

  @Test
  public void getAmount() {
    assertThat(accountService.getAccountAmount(user),is(0.0));
  }
}