package com.clouway.bank.core;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */

public class User {

  private String userName;
  private String password;
  private Account account;
  private String sessionId;

  public User(String userName, String password, Account account, String sessionId) {
    this.userName = userName;
    this.password = password;
    this.account = account;
    this.sessionId = sessionId;
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

  public String getSessionId() {
    return sessionId;
  }
}
