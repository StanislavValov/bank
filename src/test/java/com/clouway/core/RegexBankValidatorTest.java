//package com.clouway.core;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import static org.hamcrest.core.Is.is;
//import static org.junit.Assert.assertThat;
//
///**
// * Created by Stanislav Valov <hisazzul@gmail.com>
// */
//public class RegexBankValidatorTest {
//
//    RegexBankValidator regexBankValidator;
//    RegexUserValidator regexUserValidator;
//
//    @Before
//    public void setUp() throws Exception {
//        regexBankValidator = new RegexBankValidator();
//    }
//
//    @Test
//    public void correctAmount() {
//        assertThat(regexBankValidator.transactionIsValid("5.12"), is(true));
//    }
//
//    @Test
//    public void incorrectAmount() {
//        assertThat(regexBankValidator.transactionIsValid("5.555"), is(false));
//    }
//
//    @Test
//    public void dataIsCorrect() {
//        User user = new User();
//        user.setUserName("Stanislav");
//        user.setPassword("123456");
//        assertThat(regexUserValidator.userIsCorrect(user), is(true));
//    }
//
//    @Test
//    public void passwordIsShort() {
//        User user = new User();
//        user.setUserName("Stanislav");
//        user.setPassword("112");
//        assertThat(regexUserValidator.userIsCorrect(user), is(false));
//    }
//
//    @Test
//    public void passwordIsLong() {
//        User user = new User();
//        user.setUserName("Stanislav");
//        user.setPassword("12345678912323123123");
//        assertThat(regexUserValidator.userIsCorrect(user), is(false));
//    }
//
//    @Test
//    public void usernameIsShort() {
//        User user = new User();
//        user.setUserName("S");
//        user.setPassword("123456");
//        assertThat(regexUserValidator.userIsCorrect(user), is(false));
//    }
//
//    @Test
//    public void usernameIsLong() {
//        User user = new User();
//        user.setUserName("StanislavValentinovValov");
//        user.setPassword("123456");
//        assertThat(regexUserValidator.userIsCorrect(user), is(false));
//    }
//}