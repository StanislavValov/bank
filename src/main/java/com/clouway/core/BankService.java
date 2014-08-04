package com.clouway.core;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public interface BankService {

    double getAccountAmount(Session session);

    void deposit(Session session, String amount);

    void withdraw(Session session, String amount);
}