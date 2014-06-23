package com.clouway.bank.persistence;

import com.clouway.bank.core.Account;
import com.clouway.bank.core.ClockUtil;
import com.clouway.bank.core.User;
import com.google.inject.util.Providers;
import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public class PersistentBankTest {

  Mockery context = new JUnit4Mockery();
  PersistentBankService bankService;
  User user;
  Account account;
  MysqlConnectionPoolDataSource dataSource;
  Connection connection;

  ClockUtil clockUtil = context.mock(ClockUtil.class);



  @Before
  public void setUp() throws Exception {
    account = new Account("10.00");
    user = new User("Torbalan","unknown",account,"someId");
    dataSource = new MysqlConnectionPoolDataSource();

    dataSource.setUser("root");
    dataSource.setPassword("clouway.com");
    dataSource.setDatabaseName("bank");
    dataSource.setServerName("localhost");

    connection = dataSource.getConnection();
    bankService = new PersistentBankService(Providers.of(connection),clockUtil);

    bankService.cleanSessionsTable();
    bankService.cleanAccountsTable();
    bankService.registerUser(user);
  }

  @Test
  public void depositSuccess() {
    bankService.deposit(user);
    assertThat(bankService.getAccountAmount(user),is(10.0));
  }

  @Test
  public void withdraw() {
    bankService.withdraw(user);
    assertThat(bankService.getAccountAmount(user),is(0.0));
  }

  @Test
  public void userExist() {
    assertThat(bankService.userExists(user),is(true));
  }

  @Test
  public void userNotExist() {
    assertThat(bankService.userExists(new User("Ivan")),is(false));
  }
}