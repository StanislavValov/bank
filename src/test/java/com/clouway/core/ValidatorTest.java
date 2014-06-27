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
    assertThat(validator.isUserCorrect(new User("Stanislav", "123456")), is(true));
  }

  @Test
  public void passwordIsShort() {
    assertThat(validator.isUserCorrect(new User("Stanislav", "1234")), is(false));
  }

  @Test
  public void passwordIsLong() {

    assertThat(validator.isUserCorrect(new User("Stanislav", "1234567891234567890")), is(false));
  }

  @Test
  public void usernameIsShort() {
    assertThat(validator.isUserCorrect(new User("Io", "1111111")), is(false));
  }

  @Test
  public void usernameIsLong() {
    assertThat(validator.isUserCorrect(new User("abcdefghijklmnop", "11111111")), is(false));
  }
}
