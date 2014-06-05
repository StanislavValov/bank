package com.clouway.bank.core;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public class User {

  private String userName;
  private String password;
  private Account account;

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setAccount(Account account) {
    this.account = account;
  }

  public String getUserName() {
    return userName;
  }

  public String getPassword() {
    return password;
  }

  public Account getAccount() {
    return account;
  }
}
