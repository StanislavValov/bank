package com.clouway.bank.core;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public interface AccountService {

  String getPassword(User user);

  List getSessionId(String userName);

  void removeSessionId(User user);

  User findUserAssociatedWithSession(String userName);

  double getAccountAmount(User user);

  boolean userExists(User user);

  void registerUser(User user);

  void addUserAssociatedWithSession(User user);

  Timestamp getExpirationTime(User user);

  void resetSessionLife(String sessionId);
}
