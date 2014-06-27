package com.clouway.http;

import com.clouway.core.User;

import java.sql.Timestamp;
import java.util.Map;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public interface SessionService {

  void removeSessionId(String sessionId);

  User findUserAssociatedWithSession(String sessionId);

  Map<String, Timestamp> getSessionsExpirationTime();

  void resetSessionLife(String sessionId);

  int getSessionsCount();
}