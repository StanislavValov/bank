package com.clouway.persistence;

import com.clouway.core.AccountService;
import com.clouway.core.User;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by hisazzul@gmail.com on 7/15/14.
 */
public class PersistentAccountServiceIT {

    PersistentAccountService accountService;
    User user;

    @Before
    public void setUp() throws Exception {
        user = new User("Stanis;av","123456");
        user.setPassword("123456");
        accountService = new PersistentAccountService();
        accountService.deleteUser(user);
        accountService.registerUser(user);
    }

    @Test
    public void pretendThatUserExists()  {
        assertThat(accountService.userExists(user),is(true));
    }

    @Test
    public void pretendThatUserDoNotExists()  {
        User notExistingUser = new User("Chubaka","23111");
        assertThat(accountService.userExists(notExistingUser),is(false));
    }

    @Test
    public void userIsAuthorised()  {
        User authorisedUser = new User("Stanis;av","123456");
        authorisedUser.setPassword("123456");
        assertThat(accountService.isUserAuthorised(authorisedUser), is(true));
    }

    @Test
    public void wrongPasswordEntered() throws Exception {
        User notAuthorisedUser = new User("Stanis;av","123456");
        notAuthorisedUser.setPassword("123");
        assertThat(accountService.isUserAuthorised(notAuthorisedUser),is(false));
    }

    @Test
    public void wrongUserNameEntered()  {
        User notAuthorisedUser = new User("Stanislavv","123456");
        notAuthorisedUser.setPassword("123456");
        assertThat(accountService.isUserAuthorised(notAuthorisedUser),is(false));
    }
}
