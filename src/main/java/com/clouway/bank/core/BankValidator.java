package com.clouway.bank.core;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public interface BankValidator {
  boolean transactionIsValid(User user);

  boolean userNameIsValid(String userName);

  boolean passwordIsValid(String password);
}
