package com.clouway.persistence;

import com.clouway.core.SessionService;
import com.clouway.core.ClockUtil;
import com.clouway.core.User;
import com.clouway.http.AuthorisationService;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import javax.servlet.http.Cookie;
import java.sql.*;
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
    public void removeSession(String sessionId) {
        PreparedStatement preparedStatement = null;
        String sql = "delete from sessions where sessionId=?";

        try {
            preparedStatement = connectionProvider.get().prepareStatement(sql);
            preparedStatement.setString(1, sessionId);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
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

            ResultSet resultSet = preparedStatement.getResultSet();

            while (resultSet.next()) {
                searchedUser = new User(resultSet.getString("userName"), resultSet.getString("sessionId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
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
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
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
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean isUserAuthorised(User user) {
        PreparedStatement preparedStatement = null;

        String sql = "select userName,password " +
                "from accounts " +
                "where userName=?";
        try {
            preparedStatement = connectionProvider.get().prepareStatement(sql);
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.execute();

            while (preparedStatement.getResultSet().next()) {
                if (preparedStatement.getResultSet().getString("password").equals(user.getPassword())) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
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
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return 0;
    }

    @Override
    public void addUserAssociatedWithSession(User user) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connectionProvider.get().prepareStatement("insert into sessions values (?,?,?)");
            preparedStatement.setString(1, user.getSessionId());
            preparedStatement.setTimestamp(2, clockUtil.expirationDate());
            preparedStatement.setString(3, user.getUserName());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void cleanSessionsTable() {
        PreparedStatement preparedStatement = null;
        String sql = "delete from sessions";

        try {
            preparedStatement = connectionProvider.get().prepareStatement(sql);
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}