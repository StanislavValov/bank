package com.clouway.persistence;

import com.clouway.core.CalendarUtil;
import com.clouway.core.Session;
import com.clouway.core.SessionService;
import com.clouway.core.User;
import com.google.common.base.Optional;
import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Date;

/**
 * Created by hisazzul@gmail.com on 7/11/14.
 */

public class PersistentSessionService implements SessionService {

    MongoClient mongoClient;
    DBCollection sessions;
    DB database;

    @Override
    public void remove(String sessionId) {

        try {
            mongoClient = new MongoClient();
            database = mongoClient.getDB("bank");
            sessions = database.getCollection("sessions");

            BasicDBObject query = new BasicDBObject("sessionId", sessionId);

            sessions.remove(query);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Session get(String sessionId) {

        String userName = null;
        String id = null;
        Date expirationTime = null;

        try {
            mongoClient = new MongoClient();
            database = mongoClient.getDB("bank");
            sessions = database.getCollection("sessions");

            BasicDBObject query = new BasicDBObject("sessionId", sessionId);

            BasicDBObject fields = new BasicDBObject();
            fields.put("userName", 1);
            fields.put("sessionId", 2);
            fields.put("expirationTime", 3);

            DBObject session = sessions.findOne(query, fields);

            userName = String.valueOf(session.get("userName"));
            id = String.valueOf(session.get("sessionId"));
            expirationTime = (Date) session.get("expirationTime");

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        Optional<Session> optional = Optional.of(new Session(userName, id, expirationTime));
        return optional.get();
    }

    @Override
    public void reset(String sessionId) {
        try {
            mongoClient = new MongoClient();
            database = mongoClient.getDB("bank");
            sessions = database.getCollection("sessions");

            BasicDBObject query = new BasicDBObject()
                    .append("$set", new BasicDBObject()
                            .append("expirationTime", CalendarUtil.sessionExpirationTime()));

            sessions.createIndex(new BasicDBObject("sessionId", 1),
                    new BasicDBObject("expireAfterSeconds", CalendarUtil.sessionExpirationTime()));

            sessions.update(new BasicDBObject("sessionId", sessionId), query);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addUser(User user, String sessionId) {

        try {
            mongoClient = new MongoClient();
            database = mongoClient.getDB("bank");
            sessions = database.getCollection("sessions");

            BasicDBObject doc = new BasicDBObject()
                    .append("userName", user.getUserName())
                    .append("sessionId", sessionId)
                    .append("expirationTime", CalendarUtil.sessionExpirationTime());

            sessions.createIndex(new BasicDBObject("expirationTime", 1), new BasicDBObject("expireAfterSeconds", 0));
            sessions.insert(doc);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void cleanSessionsTable() {

        try {
            mongoClient = new MongoClient();
            database = mongoClient.getDB("bank");
            sessions = database.getCollection("sessions");

            sessions.drop();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}