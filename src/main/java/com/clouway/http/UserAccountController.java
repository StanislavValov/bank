package com.clouway.http;

import com.clouway.core.AccountService;
import com.clouway.core.CurrentUser;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.sitebricks.At;
import com.google.sitebricks.Show;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
//@At("/bankController")
//@Show("/bank/User.html")
//@Singleton
//public class UserAccountController {
//
//    private AccountService accountService;
//    private Provider<CurrentUser> currentUserProvider;
//
//    @Inject
//    public UserAccountController(AccountService accountService, Provider<CurrentUser> currentUserProvider) {
//        this.accountService = accountService;
//        this.currentUserProvider = currentUserProvider;
//    }
//
//    public double getUserAccountAmount() {
//        return accountService.getAccountAmount(currentUserProvider.get().getUser());
//    }
//}