package com.clouway.bank.persistence;

import com.clouway.bank.core.ClockUtil;
import com.clouway.bank.core.User;
import com.clouway.bank.http.AuthorisationService;
import com.clouway.bank.http.SessionService;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import javax.servlet.http.Cookie;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
@Singleton
public class PersistentSessionService implements SessionService, AuthorisationService {

  private final Provider<Connection> connectionProvider;
  private ClockUtil clockUtil;

  @Inject
  public PersistentSessionService(Provider<Connection> connectionProvider, ClockUtil clockUtil) {
    this.connectionProvider = connectionProvider;
    this.clockUtil = clockUtil;
  }

  @Override
  public void removeSessionId(String sessionId) {
    PreparedStatement preparedStatement = null;
    String sql = "delete from sessions where sessionId=?";

    try {
      preparedStatement = connectionProvider.get().prepareStatement(sql);
      preparedStatement.setString(1, sessionId);
      preparedStatement.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    if (preparedStatement != null) {
      try {
        preparedStatement.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public User findUserAssociatedWithSession(String sessionId) {
    PreparedStatement preparedStatement = null;
    String sql = "select userName,sessionId from sessions where sessionId=?";
    User searchedUser = null;
    try {
      preparedStatement = connectionProvider.get().prepareStatement(sql);
      preparedStatement.setString(1, sessionId);
      preparedStatement.execute();
      while (preparedStatement.getResultSet().next()) {
        searchedUser = new User(preparedStatement.getResultSet().getString("userName"), null,
                preparedStatement.getResultSet().getString("sessionId"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    if (preparedStatement != null) {
      try {
        preparedStatement.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return searchedUser;
  }

  @Override
  public Map<String, Timestamp> getSessionsExpirationTime() {
    PreparedStatement preparedStatement = null;
    String sql = "SELECT sessionId,expirationDate from sessions";
    Map<String, Timestamp> expTime = new HashMap<String, Timestamp>();

    try {
      preparedStatement = connectionProvider.get().prepareStatement(sql);
      preparedStatement.execute();
      while (preparedStatement.getResultSet().next()) {
        expTime.put(preparedStatement.getResultSet().getString("sessionId")
                , preparedStatement.getResultSet().getTimestamp("expirationDate"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    if (preparedStatement != null) {
      try {
        preparedStatement.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return expTime;
  }

  @Override
  public void resetSessionLife(String sessionId) {
    PreparedStatement preparedStatement = null;
    String sql = "UPDATE sessions set expirationDate=? where sessionId=?";

    try {
      preparedStatement = connectionProvider.get().prepareStatement(sql);
      preparedStatement.setTimestamp(1, clockUtil.expirationDate());
      preparedStatement.setString(2, sessionId);
      preparedStatement.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    if (preparedStatement != null) {
      try {
        preparedStatement.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public Cookie authenticate(User user) {
    PreparedStatement preparedStatement = null;

    Cookie cookie = null;

    String sql = "select userName,password " +
            "from accounts " +
            "where userName=?";
    try {
      preparedStatement = connectionProvider.get().prepareStatement(sql);
      preparedStatement.setString(1, user.getUserName());
      preparedStatement.execute();

      while (preparedStatement.getResultSet().next()) {
        if (preparedStatement.getResultSet().getString("password").equals(user.getPassword())) {
          cookie = new Cookie("sid", user.getSessionId());
          addUserAssociatedWithSession(user);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    if (preparedStatement != null) {
      try {
        preparedStatement.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return cookie;
  }

  @Override
  public int getSessionsCount() {
    PreparedStatement preparedStatement = null;

    String sql = "select COUNT(DISTINCT userName) from sessions limit 1";

    try {
      preparedStatement = connectionProvider.get().prepareStatement(sql);
      preparedStatement.execute();

      while (preparedStatement.getResultSet().next()) {
        return preparedStatement.getResultSet().getInt("COUNT(DISTINCT userName)");
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
    if (preparedStatement != null) {
      try {
        preparedStatement.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return 0;
  }

  public void cleanSessionsTable() {
    PreparedStatement preparedStatement = null;
    String sql = "delete from sessions";

    try {
      preparedStatement = connectionProvider.get().prepareStatement(sql);
      preparedStatement.execute();

    } catch (SQLException e) {
      e.printStackTrace();
    }
    if (preparedStatement != null) {
      try {
        preparedStatement.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  private void addUserAssociatedWithSession(User user) {
    PreparedStatement preparedStatement = null;
    try {
      preparedStatement = connectionProvider.get().prepareStatement("insert into sessions values (?,?,?)");
      preparedStatement.setString(1, user.getSessionId());
      preparedStatement.setTimestamp(2, clockUtil.expirationDate());
      preparedStatement.setString(3, user.getUserName());
      preparedStatement.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    if (preparedStatement != null) {
      try {
        preparedStatement.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}