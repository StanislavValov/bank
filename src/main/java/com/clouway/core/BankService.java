package com.clouway.core;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public interface BankService {

    double getAccountAmount(User user);

    void deposit(User user, String amount);

    void withdraw(User user, String amount);
}