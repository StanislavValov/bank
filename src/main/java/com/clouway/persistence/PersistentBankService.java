package com.clouway.persistence;

import com.clouway.core.BankService;
import com.clouway.core.Session;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.mongodb.*;

/**
 * Created by hisazzul@gmail.com on 7/14/14.
 */
@Singleton
class PersistentBankService implements BankService {

    private Provider<DB> mongoClientProvider;
    private DBCollection accounts;
    private DB database;

    @Inject
    public PersistentBankService(Provider<DB> mongoClientProvider) {
        this.mongoClientProvider = mongoClientProvider;
    }

    @Override
    public void deposit(Session session, String amount) {

        database = mongoClientProvider.get();
        database.requestStart();
        accounts = database.getCollection("accounts");

        BasicDBObject query = new BasicDBObject().append("$inc", new BasicDBObject().append("amount", Double.parseDouble(amount)));
        accounts.update(new BasicDBObject().append("userName", session.getUserName()), query);
    }

    @Override
    public void withdraw(Session session, String amount) {

        database = mongoClientProvider.get();
        database.requestStart();
        accounts = database.getCollection("accounts");

        BasicDBObject query = new BasicDBObject().append("$inc", new BasicDBObject().append("amount", -Double.parseDouble(amount)));
        accounts.update(new BasicDBObject().append("userName", session.getUserName()), query);
    }

    @Override
    public double getAccountAmount(Session session) {

        database = mongoClientProvider.get();
        accounts = database.getCollection("accounts");

        DBObject query = new BasicDBObject("userName", session.getUserName());

        DBObject fields = new BasicDBObject("amount",1);

        return Double.parseDouble(String.valueOf(accounts.findOne(query, fields).get("amount")));
    }
}