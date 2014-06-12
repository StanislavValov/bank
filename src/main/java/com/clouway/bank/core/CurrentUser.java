package com.clouway.bank.core;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public class CurrentUser {

  private User user;

  public CurrentUser(User user) {
    this.user = user;
  }

  public String getUserName() {
    return user.getUserName();
  }

}
