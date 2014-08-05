package com.clouway.core;

import com.clouway.core.User;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public interface SessionRepository {

    void remove(String sessionId);

    Session get(String sessionId);

    void reset(String sessionId);

    void addUser(User user, String sessionId);
}