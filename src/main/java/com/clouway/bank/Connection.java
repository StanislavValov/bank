package com.clouway.bank;

import java.sql.SQLException;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public interface Connection {

  void createAccount(String userName, String pass) throws SQLException;

  String getPassword(String userName);

  double getCurrency(String userName);

  void deposit(String userName, Double value);

  void withdraw(String userName, Double value);
}
