package com.clouway.bank.core;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public interface AccountService {

  String getPassword(User user);

  String getSessionId(String userName);

  void removeSessionId(String userName);

  User findUserAssociatedWithSession(String sessionId);

  double getAccountAmount(String userName);

  boolean userExists(User user);

  void registerUser(User user);

  void addUserAssociatedWithSession(User user);
}
