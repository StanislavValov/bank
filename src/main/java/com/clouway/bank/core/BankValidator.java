package com.clouway.bank.core;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public interface BankValidator {

  boolean transactionIsValid(User user);

  boolean userDataAreValid(User user);
}
