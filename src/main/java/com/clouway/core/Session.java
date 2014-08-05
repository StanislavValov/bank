package com.clouway.core;

import java.util.Date;

/**
 * Created by hisazzul@gmail.com on 7/24/14.
 */
public class Session {

    private String userName;
    private String id;
    private Date expirationDate;


    public Session(String userName, String id, Date expirationDate) {
        this.userName = userName;
        this.id = id;
        this.expirationDate = expirationDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public String getUserName() {
        return userName;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Session session = (Session) o;

        if (!id.equals(session.id)) return false;
        if (!userName.equals(session.userName)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userName.hashCode();
        result = 31 * result + id.hashCode();
        result = 31 * result + expirationDate.hashCode();
        return result;
    }
}