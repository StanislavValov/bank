//package com.clouway.persistence;
//
//import com.clouway.core.Session;
//import com.clouway.core.User;
//import com.google.inject.Provider;
//import com.google.inject.util.Providers;
//import com.mongodb.DB;
//import com.mongodb.MongoClient;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.Date;
//
//import static org.hamcrest.Matchers.is;
//import static org.junit.Assert.assertThat;
//
///**
// * Created by hisazzul@gmail.com on 7/16/14.
// */
//public class PersistentBankServiceTest {
//
//    PersistentBankService bankService;
//    PersistentUserRepository accountService;
//    User user;
//    Session session;
//    MongoClient mongoClient;
//    DB database;
//
//    @Before
//    public void setUp() throws Exception {
//        session = new Session("Torbalan","123",new Date());
//        user = new User("Torbalan");
//        mongoClient = new MongoClient();
//        database = mongoClient.getDB("bankTest");
//        bankService = new PersistentBankService(Providers.of(database));
//        accountService = new PersistentUserRepository(Providers.of(database));
//
//        accountService.delete(user.getUserName());
//        accountService.register(user);
//        bankService.deposit(session,"5");
//    }
//
//    @Test
//    public void depositSuccessful()  {
//        bankService.deposit(session,"5");
//        assertThat(bankService.getAccountAmount(session), is(10.0));
//    }
//
//    @Test
//    public void withdrawSuccessful()  {
//        bankService.withdraw(session,"5");
//        assertThat(bankService.getAccountAmount(session),is(0.0));
//    }
//
//
//}