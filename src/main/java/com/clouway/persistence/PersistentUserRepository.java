package com.clouway.persistence;

import com.clouway.core.UserRepository;
import com.clouway.core.User;
import com.clouway.core.AuthorisationService;
import com.mongodb.*;

import java.net.UnknownHostException;

/**
 * Created by hisazzul@gmail.com on 7/14/14.
 */

public class PersistentUserRepository implements UserRepository, AuthorisationService {

    private MongoClient mongoClient;
    private DBCollection accounts;
    private DB database;

    @Override
    public boolean exists(String userName) {
        Boolean userExists = false;

        try {
            mongoClient = new MongoClient();
            database = mongoClient.getDB("bank");
            accounts = database.getCollection("accounts");

            DBObject users = new BasicDBObject("userName", userName);
            userExists = accounts.count(users)==1;

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return userExists;
    }

    @Override
    public void register(User user) {

        try {
            mongoClient = new MongoClient();
            database = mongoClient.getDB("bank");
            database.requestStart();
            accounts = database.getCollection("accounts");


            BasicDBObject doc = new BasicDBObject()
                    .append("userName", user.getUserName())
                    .append("password", user.getPassword())
                    .append("amount", 0);
            accounts.createIndex(new BasicDBObject("userName", 1), new BasicDBObject("unique", true));
            accounts.insert(doc);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isAuthorised(User user) {

        Boolean isAuthorised=false;

        try {
            mongoClient = new MongoClient();
            database = mongoClient.getDB("bank");
            accounts = database.getCollection("accounts");

            BasicDBObject query = new BasicDBObject();

            query.append("userName", user.getUserName());
            query.append("password",user.getPassword());

            isAuthorised = accounts.count(query)==1;

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return isAuthorised;
    }

    public void delete(String userName) {
        try {
            mongoClient = new MongoClient();
            database = mongoClient.getDB("bank");
            accounts = database.getCollection("accounts");

            BasicDBObject query = new BasicDBObject("userName", userName);

            accounts.remove(query);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}