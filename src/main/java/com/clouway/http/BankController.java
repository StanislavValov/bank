package com.clouway.http;

import com.clouway.core.*;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.sitebricks.At;
import com.google.sitebricks.Show;
import com.google.sitebricks.http.As;
import com.google.sitebricks.http.Get;
import com.google.sitebricks.http.Post;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
@At("/bankController")
@Show("/bank/User.html")
@Singleton
public class BankController {

    private BankService bankService;
    private BankValidator validator;
    private Provider<CurrentUser> currentUserProvider;
    private SiteMap siteMap;
    private Account account = new Account();

    @Inject
    public BankController(BankService bankService, BankValidator validator, Provider<CurrentUser> currentUserProvider, SiteMap siteMap) {
        this.bankService = bankService;
        this.validator = validator;
        this.currentUserProvider = currentUserProvider;
        this.siteMap = siteMap;
    }

    @Post
    public String accountOperation() {

        User user = currentUserProvider.get().getUser();

        if (validator.isAmountValid(account.getTransactionAmount())) {

            if (account.getDeposit() != null) {
                account.setDeposit(null);
                bankService.deposit(user,account.getTransactionAmount());
            }

            if (account.getWithdraw() != null) {
                account.setWithdraw(null);
                bankService.withdraw(user, account.getTransactionAmount());
            }

        } else {
            return siteMap.transactionErrorLabel();
        }
        return siteMap.bankController();
    }

    public double getUserAccountAmount() {
        return bankService.getAccountAmount(currentUserProvider.get().getUser());
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}