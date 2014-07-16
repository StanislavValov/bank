package com.clouway.core;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public class UserEqualityTest {

  @Test
  public void usersAreEqual() {
    assertThat(new User("Stan","1122"), is((new User("Stan","1122"))));
  }

  @Test
  public void usersAreNotEqual() {
    assertThat(new User("Stan","1122"), is(not(new User("Torbalan","1122"))));
  }
}