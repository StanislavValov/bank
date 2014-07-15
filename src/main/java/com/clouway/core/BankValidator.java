package com.clouway.core;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public interface BankValidator {

  boolean isAmountValid(double amount);

  boolean isUserCorrect(User user);
}
