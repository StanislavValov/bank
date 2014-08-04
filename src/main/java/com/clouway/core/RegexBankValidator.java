package com.clouway.core;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public class RegexBankValidator implements BankValidator {

  @Override
  public boolean transactionIsValid(String amount) {
    return String.valueOf(amount).matches("^[1-9][0-9]*(\\.[0-9]{1,2})?$");
  }
}