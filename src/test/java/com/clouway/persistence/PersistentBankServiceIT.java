package com.clouway.persistence;

import com.clouway.core.User;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by hisazzul@gmail.com on 7/16/14.
 */
public class PersistentBankServiceIT {

    PersistentBankService bankService;
    PersistentAccountService accountService;
    User user;

    @Before
    public void setUp() throws Exception {

        user = new User("Torbalan","123456");
        bankService = new PersistentBankService();
        accountService = new PersistentAccountService();
        accountService.deleteUser(user);
        accountService.registerUser(user);
        bankService.deposit(user,5);
    }

    @Test
    public void depositSuccessful()  {
        bankService.deposit(user,5);
        assertThat(bankService.getAccountAmount(user), is(10.0));
    }

    @Test
    public void withdrawSuccessful()  {
        bankService.withdraw(user,5);
        assertThat(bankService.getAccountAmount(user),is(0.0));
    }
}