package com.clouway.persistence;

import com.clouway.core.BankService;
import com.clouway.core.Session;
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
    public void deposit(Session session, String amount) {

        try {
            mongoClient = new MongoClient();
            database = mongoClient.getDB("bank");
            database.requestStart();
            accounts = database.getCollection("accounts");

            BasicDBObject query = new BasicDBObject().append("$inc", new BasicDBObject().append("amount", Double.parseDouble(amount)));
            accounts.update(new BasicDBObject().append("userName", session.getUserName()), query);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void withdraw(Session session, String amount) {

        try {
            mongoClient = new MongoClient();
            database = mongoClient.getDB("bank");
            database.requestStart();
            accounts = database.getCollection("accounts");

            BasicDBObject query = new BasicDBObject().append("$inc", new BasicDBObject().append("amount", -Double.parseDouble(amount)));
            accounts.update(new BasicDBObject().append("userName", session.getUserName()), query);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Override
    public double getAccountAmount(Session session) {

        try {
            mongoClient = new MongoClient();
            database = mongoClient.getDB("bank");
            accounts = database.getCollection("accounts");

            DBObject query = new BasicDBObject("userName", session.getUserName());

            DBObject fields = new BasicDBObject("amount",1);

            return Double.parseDouble(String.valueOf(accounts.findOne(query, fields).get("amount")));

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return 0;
    }
}