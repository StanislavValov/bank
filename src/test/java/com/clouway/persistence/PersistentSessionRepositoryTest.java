//package com.clouway.persistence;
//
//import com.clouway.core.CalendarUtil;
//import com.clouway.core.Session;
//import com.clouway.core.User;
//import com.google.inject.util.Providers;
//import com.mongodb.DB;
//import com.mongodb.MongoClient;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.Date;
//
//import static org.hamcrest.Matchers.is;
//import static org.junit.Assert.assertNull;
//import static org.junit.Assert.assertThat;
//
///**
// * Created by hisazzul@gmail.com on 7/16/14.
// */
//public class PersistentSessionRepositoryTest {
//
//    PersistentSessionRepository sessionService;
//    PersistentUserRepository accountService;
//    User user;
//    Session session;
//    MongoClient mongoClient;
//    DB database;
//
//    @Before
//    public void setUp() throws Exception {
//        user = new User("Thor");
//        session = new Session("Thor","someId",new Date());
//        mongoClient = new MongoClient();
//        database = mongoClient.getDB("bankTest");
//
//        sessionService = new PersistentSessionRepository(Providers.of(database));
//        accountService = new PersistentUserRepository(Providers.of(database));
//
//        accountService.delete(user.getUserName());
//        accountService.register(user);
//        sessionService.addUser(user, session.getId());
//    }
//
//    @Test
//    public void get() {
//        assertThat(sessionService.get("someId"), is(session));
//    }
//
//    @Test
//    public void sessionLifeWasReset() {
//        sessionService.reset(session.getId());
//        assertThat(sessionService.get("someId").getExpirationDate(), is(CalendarUtil.sessionExpirationTime()));
//    }
//
//    @Test(expected = NullPointerException.class)
//    public void sessionDoesNotExists() {
//        sessionService.remove(session.getId());
//        assertNull(sessionService.get(session.getId()));
//    }
//}