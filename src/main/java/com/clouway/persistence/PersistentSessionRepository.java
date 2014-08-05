package com.clouway.persistence;

import com.clouway.core.CalendarUtil;
import com.clouway.core.Session;
import com.clouway.core.SessionRepository;
import com.clouway.core.User;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.mongodb.*;

import java.util.Date;

/**
 * Created by hisazzul@gmail.com on 7/11/14.
 */
@Singleton
class PersistentSessionRepository implements SessionRepository {

    Provider<DB> mongoClientProvider;
    DBCollection sessions;
    DB database;

    @Inject
    public PersistentSessionRepository(Provider<DB> mongoClientProvider) {
        this.mongoClientProvider = mongoClientProvider;
    }

    @Override
    public void remove(String sessionId) {

        database = mongoClientProvider.get();
        sessions = database.getCollection("sessions");
        BasicDBObject query = new BasicDBObject("sessionId", sessionId);
        sessions.remove(query);
    }

    @Override
    public Session get(String sessionId) {

        database = mongoClientProvider.get();
        sessions = database.getCollection("sessions");

        BasicDBObject query = new BasicDBObject("sessionId", sessionId);

        BasicDBObject fields = new BasicDBObject();
        fields.put("userName", 1);
        fields.put("sessionId", 2);
        fields.put("expirationTime", 3);

        DBObject session = sessions.findOne(query, fields);

        String userName = String.valueOf(session.get("userName"));
        String id = String.valueOf(session.get("sessionId"));
        Date expirationTime = (Date) session.get("expirationTime");

        return new Session(userName, id, expirationTime);
    }

    @Override
    public void reset(String sessionId) {
        database = mongoClientProvider.get();
        sessions = database.getCollection("sessions");

        BasicDBObject query = new BasicDBObject()
                .append("$set", new BasicDBObject()
                        .append("expirationTime", CalendarUtil.sessionExpirationTime()));

        sessions.createIndex(new BasicDBObject("sessionId", 1),
                new BasicDBObject("expireAfterSeconds", CalendarUtil.sessionExpirationTime()));

        sessions.update(new BasicDBObject("sessionId", sessionId), query);
    }

    @Override
    public void addUser(User user, String sessionId) {

        database = mongoClientProvider.get();
        sessions = database.getCollection("sessions");

        BasicDBObject doc = new BasicDBObject()
                .append("userName", user.getUserName())
                .append("sessionId", sessionId)
                .append("expirationTime", CalendarUtil.sessionExpirationTime());

        sessions.createIndex(new BasicDBObject("expirationTime", 1), new BasicDBObject("expireAfterSeconds", 0));
        sessions.insert(doc);
    }
}