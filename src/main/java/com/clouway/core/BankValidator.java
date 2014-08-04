package com.clouway.core;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public interface BankValidator {

  boolean transactionIsValid(String amount);
}