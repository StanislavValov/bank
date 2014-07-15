package com.clouway.persistence;

import com.clouway.core.ClockUtil;
import com.clouway.core.SessionService;
import com.clouway.core.User;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mongodb.*;

import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hisazzul@gmail.com on 7/11/14.
 */
@Singleton
public class MongoDbSessionService implements SessionService {

    MongoClient mongoClient;
    DBCollection sessions;
    ClockUtil clockUtil;
    DB database;

    @Inject
    public MongoDbSessionService(ClockUtil clockUtil) {
        this.clockUtil = clockUtil;
    }

    @Override
    public void removeSession(String sessionId) {

        try {
            mongoClient = new MongoClient();
            database = mongoClient.getDB("bank");
            sessions = database.getCollection("sessions");

            DBObject field = sessions.findOne(sessionId);
            sessions.remove(field);

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
    public Map<String, Timestamp> getSessionsExpirationTime() {

        Map<String, Timestamp> result = new HashMap<String, Timestamp>();

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
                result.put((String) dbObject.get("sessionId"), (Timestamp) dbObject.get("expirationTime"));
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
        return 0;
    }

    @Override
    public void addUserAssociatedWithSession(User user) {
        try {
            mongoClient = new MongoClient();
            database = mongoClient.getDB("bank");
            sessions = database.getCollection("sessions");

            BasicDBObject doc = new BasicDBObject()
                    .append("userName", user.getUserName())
                    .append("sessionId", user.getSessionId())
                    .append("expirationTime", clockUtil.expirationDate());
            sessions.insert(doc);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}