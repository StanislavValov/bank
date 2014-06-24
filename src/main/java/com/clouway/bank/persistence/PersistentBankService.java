package com.clouway.bank.persistence;

import com.clouway.bank.core.BankService;
import com.clouway.bank.core.User;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
@Singleton
public class PersistentBankService implements BankService {

  private final Provider<Connection> connectionProvider;

  @Inject
  public PersistentBankService(Provider<Connection> connectionProvider) {
    this.connectionProvider = connectionProvider;
  }

  @Override
  public void deposit(User user) {
    PreparedStatement preparedStatement = null;
    try {
      preparedStatement = connectionProvider.get().prepareStatement
              ("update accounts set amount=amount+? where userName=?");
      preparedStatement.setString(1, user.getAccount().getTransactionAmount());
      preparedStatement.setString(2, user.getUserName());
      preparedStatement.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    if (preparedStatement != null) {
      try {
        preparedStatement.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void withdraw(User user) {
    PreparedStatement preparedStatement = null;
    try {
      preparedStatement = connectionProvider.get().prepareStatement
              ("update accounts set amount=amount-? where userName=? and " + user.getAccount().getTransactionAmount() + " <= amount");
      preparedStatement.setString(1, user.getAccount().getTransactionAmount());
      preparedStatement.setString(2, user.getUserName());
      preparedStatement.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    if (preparedStatement != null) {
      try {
        preparedStatement.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}