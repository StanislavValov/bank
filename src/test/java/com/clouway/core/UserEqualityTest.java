//package com.clouway.core;
//
//import org.junit.Test;
//
//import static org.hamcrest.core.Is.is;
//import static org.hamcrest.core.IsNot.not;
//import static org.junit.Assert.assertThat;
//
///**
// * Created by Stanislav Valov <hisazzul@gmail.com>
// */
//public class UserEqualityTest {
//
//  @Test
//  public void usersAreEqual() {
//    assertThat(new User("Stan"), is((new User("Stan"))));
//  }
//
//  @Test
//  public void usersAreNotEqual() {
//    assertThat(new User("Stan"), is(not(new User("Torbalan"))));
//  }
//}