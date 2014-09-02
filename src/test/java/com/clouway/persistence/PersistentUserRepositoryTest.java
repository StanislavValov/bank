//package com.clouway.persistence;
//
//import com.clouway.core.User;
//import com.google.inject.util.Providers;
//import com.mongodb.DB;
//import com.mongodb.MongoClient;
//import org.junit.Before;
//import org.junit.Test;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.core.Is.is;
//
///**
// * Created by hisazzul@gmail.com on 7/15/14.
// */
//public class PersistentUserRepositoryTest {
//
//    PersistentUserRepository accountService;
//    User user;
//    MongoClient mongoClient;
//    DB dataBase;
//
//    @Before
//    public void setUp() throws Exception {
//        mongoClient = new MongoClient();
//        dataBase = mongoClient.getDB("bankTest");
//        user = new User("Stanis;av");
//        user.setPassword("123456");
//        accountService = new PersistentUserRepository(Providers.of(dataBase));
//        accountService.delete(user.getUserName());
//        accountService.register(user);
//    }
//
//    @Test
//    public void pretendThatUserExists()  {
//        assertThat(accountService.exists(user.getUserName()),is(true));
//    }
//
//    @Test
//    public void pretendThatUserDoNotExists()  {
//        User notExistingUser = new User("Chubaka");
//        assertThat(accountService.exists(notExistingUser.getUserName()),is(false));
//    }
//
//    @Test
//    public void userIsAuthorised()  {
//        User authorisedUser = new User("Stanis;av");
//        authorisedUser.setPassword("123456");
//        assertThat(accountService.isAuthorised(authorisedUser), is(true));
//    }
//
//    @Test
//    public void wrongPasswordEntered() throws Exception {
//        User notAuthorisedUser = new User("Stanis;av");
//        notAuthorisedUser.setPassword("123");
//        assertThat(accountService.isAuthorised(notAuthorisedUser),is(false));
//    }
//
//    @Test
//    public void wrongUserNameEntered()  {
//        User notAuthorisedUser = new User("Stanislavv");
//        notAuthorisedUser.setPassword("123456");
//        assertThat(accountService.isAuthorised(notAuthorisedUser),is(false));
//    }
//}