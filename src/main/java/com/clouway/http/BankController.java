package com.clouway.http;

import com.clouway.core.*;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.sitebricks.At;
import com.google.sitebricks.Show;
import com.google.sitebricks.http.Post;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
@At("/bankController")
@Show("/bank/User.html")
@Singleton
public class BankController {

    private BankService bankService;
    private BankValidator validator;
    private Provider<Session> currentSessionProvider;
    private SiteMap siteMap;
    private Account account = new Account();

    @Inject
    public BankController(BankService bankService, BankValidator validator, Provider<Session> currentSessionProvider, SiteMap siteMap) {
        this.bankService = bankService;
        this.validator = validator;
        this.currentSessionProvider = currentSessionProvider;
        this.siteMap = siteMap;
    }

    @Post
    public String accountOperation() {

        Session currentSession= currentSessionProvider.get();

        if (validator.amountIsValid(account.getTransactionAmount())) {

            if (account.getDeposit() != null) {
                account.setDeposit(null);
                bankService.deposit(currentSession, account.getTransactionAmount());
            }

            if (account.getWithdraw() != null) {
                account.setWithdraw(null);
                bankService.withdraw(currentSession, account.getTransactionAmount());
            }

        } else {
            return siteMap.transactionErrorLabel();
        }
        return siteMap.bankController();
    }

    public double getUserAccountAmount() {
        return bankService.getAccountAmount(currentSessionProvider.get());
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}