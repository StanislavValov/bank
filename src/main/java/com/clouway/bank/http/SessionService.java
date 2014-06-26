package com.clouway.bank.http;

import com.clouway.bank.core.User;

import java.sql.Timestamp;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public interface SessionService {

  void removeSessionId(String sessionId);

  User findUserAssociatedWithSession(String sessionId);

  Timestamp getSessionExpirationTime(String sessionId);

  void resetSessionLife(String sessionId);
}