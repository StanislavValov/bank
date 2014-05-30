package com.clouway.bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public class DbConnection implements com.clouway.bank.Connection {

  String url = "jdbc:mysql://127.0.0.1/bank";
  Connection connection = null;

  @Override
  public void createAccount(String userName, String pass) throws SQLException {
    PreparedStatement preparedStatement = null;

    connection = DriverManager.getConnection(url, "root", "clouway.com");
    preparedStatement = connection.prepareStatement("Insert into accounts values(?,?,?)");
    preparedStatement.setString(1, userName);
    preparedStatement.setString(2, pass);
    preparedStatement.setDouble(3, 0);
    preparedStatement.execute();


    try {
      preparedStatement.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public String getPassword(String userName) {

    PreparedStatement preparedStatement = null;
    String password = null;
    String sql = "select password from accounts where userName='" + userName + "'";

    try {
      connection = DriverManager.getConnection(url, "root", "clouway.com");
      preparedStatement = connection.prepareStatement(sql);
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
  public double getCurrency(String userName) {
    PreparedStatement preparedStatement = null;
    String sql = "select currency from accounts where userName='" + userName + "'";
    double result = 0.0;
    try {
      connection = DriverManager.getConnection(url, "root", "clouway.com");
      preparedStatement = connection.prepareStatement(sql);
      preparedStatement.execute();
      while (preparedStatement.getResultSet().next()) {
        result = preparedStatement.getResultSet().getDouble("currency");
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
  public void deposit(String userName, Double value) {
    PreparedStatement preparedStatement = null;
    try {
      connection = DriverManager.getConnection(url, "root", "clouway.com");
      preparedStatement = connection.prepareStatement
              ("update accounts set currency=currency+" + value + " where userName='" + userName + "'");
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
  public void withdraw(String userName, Double value) {
    PreparedStatement preparedStatement = null;
    try {
      connection = DriverManager.getConnection(url, "root", "clouway.com");
      preparedStatement = connection.prepareStatement
              ("update accounts set currency=currency-" + value + " where userName='" + userName + "' " +
                      "and "+value+" <= currency");
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
