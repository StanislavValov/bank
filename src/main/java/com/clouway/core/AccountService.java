package com.clouway.core;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public interface AccountService {

  double getAccountAmount(User user);

  boolean userExists(User user);

  void registerUser(User user);
}
