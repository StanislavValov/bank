package com.clouway.bank.http;

import com.clouway.bank.core.User;

import javax.servlet.http.Cookie;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public interface SessionService {

  List getSessionIds(String userName);

  void removeSessionId(User user);

  User findUserAssociatedWithSession(String userName);

  Timestamp getSessionExpirationTime(User user);

  void resetSessionLife(User user);

  Cookie authenticate(User user);
}