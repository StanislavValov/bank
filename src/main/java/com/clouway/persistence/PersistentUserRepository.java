package com.clouway.persistence;

import com.clouway.core.UserRepository;
import com.clouway.core.User;
import com.clouway.core.AuthorisationService;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.mongodb.*;

import java.net.UnknownHostException;

/**
 * Created by hisazzul@gmail.com on 7/14/14.
 */
@Singleton
class PersistentUserRepository implements UserRepository, AuthorisationService {

    private Provider<DB> mongoClientProvider;
    private DBCollection accounts;
    private DB database;

    @Inject
    public PersistentUserRepository(Provider<DB> mongoClientProvider) {
        this.mongoClientProvider = mongoClientProvider;
    }

    @Override
    public boolean exists(String userName) {

        database = mongoClientProvider.get();
        accounts = database.getCollection("accounts");

        DBObject users = new BasicDBObject("userName", userName);

        return accounts.count(users)==1;
    }

    @Override
    public void register(User user) {

        database = mongoClientProvider.get();
        database.requestStart();
        accounts = database.getCollection("accounts");

        BasicDBObject doc = new BasicDBObject()
                .append("userName", user.getUserName())
                .append("password", user.getPassword())
                .append("amount", 0);
        accounts.createIndex(new BasicDBObject("userName", 1), new BasicDBObject("unique", true));
        accounts.insert(doc);
    }

    @Override
    public boolean isAuthorised(User user) {

        database = mongoClientProvider.get();
        accounts = database.getCollection("accounts");

        BasicDBObject query = new BasicDBObject();

        query.append("userName", user.getUserName());
        query.append("password",user.getPassword());

        return accounts.count(query)==1;
    }

    public void delete(String userName) {
        database = mongoClientProvider.get();
        accounts = database.getCollection("accounts");

        BasicDBObject query = new BasicDBObject("userName", userName);

        accounts.remove(query);
    }
}