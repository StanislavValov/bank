package com.clouway.bank.core;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public interface BankService {

  void deposit(User user, String amount);

  void withdraw(User user, String amount);
}
