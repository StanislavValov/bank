package com.clouway.core;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public interface BankService {

    double getAccountAmount(User user);

    void deposit(User user, double amount);

    void withdraw(User user, double amount);
}