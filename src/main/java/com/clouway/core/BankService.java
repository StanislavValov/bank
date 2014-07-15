package com.clouway.core;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public interface BankService {

  void deposit(User user, double amount);

  void withdraw(User user, double amount);
}