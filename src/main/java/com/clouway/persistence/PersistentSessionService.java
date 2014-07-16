package com.clouway.persistence;

import com.clouway.core.ClockUtil;
import com.clouway.core.SessionService;
import com.clouway.core.User;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hisazzul@gmail.com on 7/11/14.
 */
@Singleton
public class PersistentSessionService implements SessionService {

    MongoClient mongoClient;
    DBCollection sessions;
    ClockUtil clockUtil;
    DB database;

    @Inject
    public PersistentSessionService(ClockUtil clockUtil) {
        this.clockUtil = clockUtil;
    }

    @Override
    public void removeSession(String sessionId) {

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
    public User findUserAssociatedWithSession(String sessionId) {

        try {
            mongoClient = new MongoClient();
            database = mongoClient.getDB("bank");
            sessions = database.getCollection("sessions");

            BasicDBObject query = new BasicDBObject();
            query.append("sessionId", sessionId);

            BasicDBObject fields = new BasicDBObject();
            fields.put("userName", 1);
            fields.put("sessionId", 2);

            DBCursor cursor = sessions.find(query, fields);

            while (cursor.hasNext()) {
                BasicDBObject dbObject = (BasicDBObject) cursor.next();
                return new User(dbObject.getString("userName"), dbObject.getString("sessionId"));
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, Date> getSessionsExpirationTime() {

        Map<String, Date> result = new HashMap<String, Date>();

        try {
            mongoClient = new MongoClient();
            database = mongoClient.getDB("bank");
            sessions = database.getCollection("sessions");

            BasicDBObject query = new BasicDBObject();

            BasicDBObject fields = new BasicDBObject();
            fields.put("sessionId", 1);
            fields.put("expirationTime", 2);

            DBCursor cursor = sessions.find(query, fields);

            while (cursor.hasNext()) {
                BasicDBObject dbObject = (BasicDBObject) cursor.next();
                result.put((String) dbObject.get("sessionId"), (Date) dbObject.get("expirationTime"));
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void resetSessionLife(String sessionId) {
        try {
            mongoClient = new MongoClient();
            database = mongoClient.getDB("bank");
            sessions = database.getCollection("sessions");

            BasicDBObject query = new BasicDBObject()
                    .append("$set", new BasicDBObject().append("expirationTime", clockUtil.expirationDate()));
            sessions.update(new BasicDBObject().append("sessionId", sessionId), query);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getSessionsCount() {
        int counter=0;
        try {
            mongoClient = new MongoClient();
            database = mongoClient.getDB("bank");
            sessions = database.getCollection("sessions");
            counter = (int) sessions.count();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return counter;
    }

    @Override
    public void addUserAssociatedWithSession(User user, String sessionId) {
        try {
            mongoClient = new MongoClient();
            database = mongoClient.getDB("bank");
            sessions = database.getCollection("sessions");

            BasicDBObject doc = new BasicDBObject()
                    .append("userName", user.getUserName())
                    .append("sessionId", sessionId)
                    .append("expirationTime", clockUtil.expirationDate());
            sessions.insert(doc);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void cleanSessionsTable(){
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