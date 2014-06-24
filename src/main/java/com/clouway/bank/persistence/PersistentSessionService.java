package com.clouway.bank.persistence;

import com.clouway.bank.core.ClockUtil;
import com.clouway.bank.core.User;
import com.clouway.bank.http.SessionService;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import javax.servlet.http.Cookie;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
@Singleton
public class PersistentSessionService implements SessionService{

  private final Provider<Connection> connectionProvider;
  private ClockUtil clockUtil;

  @Inject
  public PersistentSessionService(Provider<Connection> connectionProvider, ClockUtil clockUtil) {
    this.connectionProvider = connectionProvider;
    this.clockUtil = clockUtil;
  }

  @Override
  public List<String> getSessionIds(String userName) {
    PreparedStatement preparedStatement = null;
    String sql = "select sessionId from sessions where userName=?";
    List<String> id = new ArrayList<String>();
    try {
      preparedStatement = connectionProvider.get().prepareStatement(sql);
      preparedStatement.setString(1, userName);
      preparedStatement.execute();
      while (preparedStatement.getResultSet().next()) {
        id.add(preparedStatement.getResultSet().getString("sessionId"));
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
    return id;
  }


  @Override
  public void removeSessionId(User user) {
    PreparedStatement preparedStatement = null;
    String sql = "delete from sessions where sessionId=?";

    try {
      preparedStatement = connectionProvider.get().prepareStatement(sql);
      preparedStatement.setString(1, user.getSessionId());
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
  public User findUserAssociatedWithSession(String userName) {
    PreparedStatement preparedStatement = null;
    String sql = "select userName,sessionId from sessions where userName=?";
    User searchedUser = null;
    try {
      preparedStatement = connectionProvider.get().prepareStatement(sql);
      preparedStatement.setString(1, userName);
      preparedStatement.execute();
      while (preparedStatement.getResultSet().next()) {
        searchedUser = new User(preparedStatement.getResultSet().getString("userName"), null, null,
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
  public Timestamp getSessionExpirationTime(User user) {
    PreparedStatement preparedStatement = null;
    String sql = "SELECT expirationDate from sessions where userName=?";
    try {
      preparedStatement = connectionProvider.get().prepareStatement(sql);
      preparedStatement.setString(1, user.getUserName());
      preparedStatement.execute();
      while (preparedStatement.getResultSet().next()) {
        return preparedStatement.getResultSet().getTimestamp("expirationDate");
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
    return null;
  }

  @Override
  public void resetSessionLife(User user) {
    PreparedStatement preparedStatement = null;
    String sql = "UPDATE sessions set expirationDate=? where sessionId=?";

    try {
      preparedStatement = connectionProvider.get().prepareStatement(sql);
      preparedStatement.setTimestamp(1, clockUtil.expirationDate());
      preparedStatement.setString(2, user.getSessionId());
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
        if (preparedStatement.getResultSet().getString("password").equals(user.getPassword())){
          cookie = new Cookie(preparedStatement.getResultSet().getString("userName"),user.getSessionId());
          addUserAssociatedWithSession(user);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    if (preparedStatement!=null){
      try {
        preparedStatement.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return cookie;
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
