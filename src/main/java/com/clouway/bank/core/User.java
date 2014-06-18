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

  public User(String userName) {
    this.userName = userName;
  }

  public User(String userName, Account account) {
    this.userName = userName;
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

  public String getSessionId() {
    return sessionId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    User user = (User) o;

    if (password != null && user.password != null) {
      if (!password.equals(user.password)) return false;
    }
    if (sessionId != null && user.sessionId != null) {
      if (!sessionId.equals(user.sessionId)) return false;
    }
    if (!userName.equals(user.userName)) return false;

    return true;
  }
}
