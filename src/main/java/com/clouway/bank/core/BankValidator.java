package com.clouway.bank.core;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public interface BankValidator {

  boolean isAmountValid(String amount);

  boolean isDataCorrect(User user);
}
