package com.clouway.core;

import com.clouway.core.User;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public interface SessionService {

    void removeSession(String sessionId);

    User findUserAssociatedWithSession(String sessionId);

    Map<String, Date> getSessionsExpirationTime();

    void resetSessionLife(String sessionId);

    void addUserAssociatedWithSession(User user, String sessionId);
}