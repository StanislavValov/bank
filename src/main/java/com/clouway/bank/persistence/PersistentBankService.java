package com.clouway.bank.persistence;

import com.clouway.bank.core.AccountService;
import com.clouway.bank.core.BankService;
import com.clouway.bank.core.User;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
@Singleton
public class PersistentBankService implements BankService, AccountService {

  private final Provider<Connection> connectionProvider;

  @Inject
  public PersistentBankService(Provider<Connection> connectionProvider) {
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
  }

  @Override
  public void addUserAssociatedWithSession(User user) {
    PreparedStatement preparedStatement = null;
    try {
      preparedStatement = connectionProvider.get().prepareStatement("insert into sessions values (?,?,?)");
      preparedStatement.setString(1, user.getUserName());
      preparedStatement.setString(2, user.getSessionId());
      preparedStatement.setTimestamp(3,new Timestamp(System.currentTimeMillis()));
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
  public String getPassword(User user) {
    PreparedStatement preparedStatement = null;
    String password = null;
    String sql = "select password " +
            "from accounts " +
            "where userName=?";

    try {
      preparedStatement = connectionProvider.get().prepareStatement(sql);
      preparedStatement.setString(1, user.getUserName());
      preparedStatement.execute();
      while (preparedStatement.getResultSet().next()) {
        password = preparedStatement.getResultSet().getString("password");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    try {
      if (preparedStatement != null) {
        preparedStatement.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return password;
  }

  @Override
  public String getSessionId(String userName) {
    PreparedStatement preparedStatement = null;
    String sql = "select sessionId from sessions where userName=?";
    String id = null;
    try {
      preparedStatement = connectionProvider.get().prepareStatement(sql);
      preparedStatement.setString(1, userName);
      preparedStatement.execute();
      while (preparedStatement.getResultSet().next()) {
        id = preparedStatement.getResultSet().getString("sessionId");
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
    return id;
  }

  @Override
  public void removeSessionId(String userName) {
    PreparedStatement preparedStatement = null;
    String sql ="delete from sessions where userName=?";

    try {
      preparedStatement = connectionProvider.get().prepareStatement(sql);
      preparedStatement.setString(1,userName);
      preparedStatement.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public User findUserAssociatedWithSession(String userName) {
    PreparedStatement preparedStatement = null;
    String sql = "select userName,sessionId from sessions where userName='" + userName + "' ";
    User user = null;
    try {
      preparedStatement = connectionProvider.get().prepareStatement(sql);
      preparedStatement.execute();
      while (preparedStatement.getResultSet().next()) {
        user = new User(preparedStatement.getResultSet().getString("userName"), null, null,
                preparedStatement.getResultSet().getString("sessionId"));
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
    return user;
  }

  @Override
  public double getAccountAmount(String userName) {
    PreparedStatement preparedStatement = null;
    String sql = "select amount from accounts where userName=?";
    double result = 0.0;
    try {
      preparedStatement = connectionProvider.get().prepareStatement(sql);
      preparedStatement.setString(1, userName);
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
  public void deposit(User user) {
    PreparedStatement preparedStatement = null;
    try {
      preparedStatement = connectionProvider.get().prepareStatement
              ("update accounts set amount=amount+" + user.getAccount().getTransactionAmount() + " where userName='" + user.getUserName() + "'");
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
              ("update accounts set amount=amount-" + user.getAccount().getTransactionAmount() + " where userName='" + user.getUserName() + "' " +
                      "and " + user.getAccount().getTransactionAmount() + " <= amount");
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
  public boolean userExists(User user) {
    PreparedStatement preparedStatement = null;
    boolean exists = false;
    try {
      preparedStatement = connectionProvider.get().prepareStatement("SELECT userName from accounts where userName='" + user.getUserName() + "' ");
      preparedStatement.execute();
      while (preparedStatement.getResultSet().next()) {
        exists = true;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return exists;
  }
}