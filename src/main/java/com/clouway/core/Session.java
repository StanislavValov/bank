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
}