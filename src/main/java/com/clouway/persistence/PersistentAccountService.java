package com.clouway.persistence;

import com.clouway.core.AccountService;
import com.clouway.core.User;
import com.clouway.http.AuthorisationService;
import com.mongodb.*;

import java.net.UnknownHostException;

/**
 * Created by hisazzul@gmail.com on 7/14/14.
 */
public class PersistentAccountService implements AccountService, AuthorisationService {

    private MongoClient mongoClient;
    private DBCollection accounts;
    private DB database;


    @Override
    public boolean userExists(User user) {

        try {
            mongoClient = new MongoClient();
            database = mongoClient.getDB("bank");
            accounts = database.getCollection("accounts");

            BasicDBObject query = new BasicDBObject();
            query.append("userName", user.getUserName());

            BasicDBObject field = new BasicDBObject();
            field.put("userName", user.getUserName());

            DBCursor cursor = accounts.find(query, field);
            while (cursor.hasNext()) {
                return true;
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void registerUser(User user) {

        try {
            mongoClient = new MongoClient();
            database = mongoClient.getDB("bank");
            database.requestStart();
            accounts = database.getCollection("accounts");

            BasicDBObject doc = new BasicDBObject()
                    .append("userName", user.getUserName())
                    .append("password", user.getPassword())
                    .append("amount", 0);
            accounts.insert(doc);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isUserAuthorised(User user) {

        try {
            mongoClient = new MongoClient();
            database = mongoClient.getDB("bank");
            accounts = database.getCollection("accounts");

            BasicDBObject query = new BasicDBObject();
            query.append("userName", user.getUserName());

            BasicDBObject fields = new BasicDBObject();

            fields.put("userName", 1);
            fields.put("password", 2);

            DBCursor cursor = accounts.find(query, fields);

            while (cursor.hasNext()) {
                BasicDBObject dbObject = (BasicDBObject) cursor.next();

                if (dbObject.get("password").equals(user.getPassword())){
                    return true;
                }
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void deleteUser(User user){
        try {
            mongoClient = new MongoClient();
            database = mongoClient.getDB("bank");
            accounts = database.getCollection("accounts");

            BasicDBObject query = new BasicDBObject("userName", user.getUserName());

            accounts.remove(query);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}