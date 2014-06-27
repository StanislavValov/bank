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

    assertThat(new User("Torbalan", "unknown", "123"), is((new User("Torbalan", "unknown", "123"))));
  }

  @Test
  public void usersAreNotEqual() {
    assertThat(new User("Torbalan", "known", "111"), is(not(new User("Tor", "known", "111"))));
  }
}
