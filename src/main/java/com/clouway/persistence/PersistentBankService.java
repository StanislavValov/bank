package com.clouway.persistence;

import com.clouway.core.BankService;
import com.clouway.core.User;
import com.mongodb.*;

import java.net.UnknownHostException;

/**
 * Created by hisazzul@gmail.com on 7/14/14.
 */
public class PersistentBankService implements BankService {

    private MongoClient mongoClient;
    private DBCollection accounts;
    private DB database;

    @Override
    public void deposit(User user, double amount) {

        try {
            mongoClient = new MongoClient();
            database = mongoClient.getDB("bank");
            database.requestStart();
            accounts = database.getCollection("accounts");

            BasicDBObject query = new BasicDBObject().append("$inc", new BasicDBObject().append("amount", amount));
            accounts.update(new BasicDBObject().append("userName", user.getUserName()), query);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void withdraw(User user, double amount) {

        try {
            mongoClient = new MongoClient();
            database = mongoClient.getDB("bank");
            database.requestStart();
            accounts = database.getCollection("accounts");

            BasicDBObject query = new BasicDBObject().append("$inc", new BasicDBObject().append("amount", -amount));
            accounts.update(new BasicDBObject().append("userName", user.getUserName()), query);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Override
    public double getAccountAmount(User user) {

        try {
            mongoClient = new MongoClient();
            database = mongoClient.getDB("bank");
            accounts = database.getCollection("accounts");

            BasicDBObject query = new BasicDBObject();
            query.append("userName", user.getUserName());

            BasicDBObject field = new BasicDBObject();
            field.put("amount", user.getUserName());

            DBCursor cursor = accounts.find(query, field);

            while (cursor.hasNext()) {
                BasicDBObject dbObject = (BasicDBObject) cursor.next();
                return Double.parseDouble(String.valueOf(dbObject.get("amount")));
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return 0;
    }
}