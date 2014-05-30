package com.clouway.bank;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public class Validator {

  public boolean transactionValidator(String value) {

    return value.matches("^[1-9][0-9]*(\\.[0-9]{1,2})?$");
  }
}