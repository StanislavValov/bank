package com.clouway.core;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */

public class User {

    private String userName;
    private String password;
    private String sessionId;

    public User(String userName, String sessionId) {
        this.userName = userName;
        this.sessionId = sessionId;
    }

    public User() {
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getSessionId() {
        return sessionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (password != null && user.password != null) {
            if (!password.equals(user.password)) return false;
        }
        if (sessionId != null && user.sessionId != null) {
            if (!sessionId.equals(user.sessionId)) return false;
        }
        return userName.equals(user.userName);
    }

    public void print(){
        System.out.println(123);
    }
}