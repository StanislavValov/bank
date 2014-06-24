package com.clouway.bank.persistence;

import com.clouway.bank.core.AccountService;
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
public class PersistentAccountService implements AccountService {

  private final Provider<Connection> connectionProvider;

  @Inject
  public PersistentAccountService(Provider<Connection> connectionProvider) {
    this.connectionProvider = connectionProvider;
  }

  @Override
  public void registerUser(User user) {
    PreparedStatement preparedStatement = null;
    try {
      preparedStatement = connectionProvider.get().prepareStatement("Insert into accounts values(?,?,?)");
      preparedStatement.setString(1, user.getUserName());
      preparedStatement.setString(2, user.getPassword());
      preparedStatement.setDouble(3, 0);
      preparedStatement.execute();

      preparedStatement.close();
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
  public double getAccountAmount(User user) {
    PreparedStatement preparedStatement = null;
    String sql = "select amount from accounts where userName=?";
    double result = 0.0;
    try {
      preparedStatement = connectionProvider.get().prepareStatement(sql);
      preparedStatement.setString(1, user.getUserName());
      preparedStatement.execute();
      while (preparedStatement.getResultSet().next()) {
        result = preparedStatement.getResultSet().getDouble("amount");
      }
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
    return result;
  }

  @Override
  public boolean userExists(User user) {
    PreparedStatement preparedStatement = null;
    boolean exists = false;
    try {
      preparedStatement = connectionProvider.get().prepareStatement("SELECT userName from accounts where userName=?");
      preparedStatement.setString(1,user.getUserName());
      preparedStatement.execute();
      while (preparedStatement.getResultSet().next()) {
        exists = true;
      }
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
    return exists;
  }

  public void cleanAccountsTable() {
    PreparedStatement preparedStatement = null;
    String sql = "delete from accounts";

    try {
      preparedStatement = connectionProvider.get().prepareStatement(sql);
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
