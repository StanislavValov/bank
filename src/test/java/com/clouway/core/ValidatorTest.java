package com.clouway.core;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public class ValidatorTest {

    Validator validator;

    @Before
    public void setUp() throws Exception {
        validator = new Validator();
    }

    @Test
    public void correctAmount() {
        assertThat(validator.isAmountValid("5.12"), is(true));
    }

    @Test
    public void incorrectAmount() {
        assertThat(validator.isAmountValid("5.555"), is(false));
    }

    @Test
    public void dataIsCorrect() {
        User user = new User();
        user.setUserName("Stanislav");
        user.setPassword("123456");
        assertThat(validator.isUserCorrect(user), is(true));
    }

    @Test
    public void passwordIsShort() {
        User user = new User();
        user.setUserName("Stanislav");
        user.setPassword("112");
        assertThat(validator.isUserCorrect(user), is(false));
    }

    @Test
    public void passwordIsLong() {
        User user = new User();
        user.setUserName("Stanislav");
        user.setPassword("12345678912323123123");
        assertThat(validator.isUserCorrect(user), is(false));
    }

    @Test
    public void usernameIsShort() {
        User user = new User();
        user.setUserName("S");
        user.setPassword("123456");
        assertThat(validator.isUserCorrect(user), is(false));
    }

    @Test
    public void usernameIsLong() {
        User user = new User();
        user.setUserName("StanislavValentinovValov");
        user.setPassword("123456");
        assertThat(validator.isUserCorrect(user), is(false));
    }
}