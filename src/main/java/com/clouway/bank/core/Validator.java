package com.clouway.bank.core;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public class Validator implements BankValidator {

  @Override
  public boolean isAmountValid(String amount) {
    return amount.matches("^[1-9][0-9]*(\\.[0-9]{1,2})?$");
  }

  @Override
  public boolean isUserCorrect(User user){
    return user.getUserName().matches("^[A-Za-z]{5,15}?$") &&
            user.getPassword().matches("^[0-9a-zA-Z]{6,18}?$");
  }
}