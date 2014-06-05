package com.clouway.bank.core;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public class Validator implements BankValidator {

  @Override
  public boolean transactionIsValid(User user) {
    return user.getAccount().getTransactionAmount().matches("^[1-9][0-9]*(\\.[0-9]{1,2})?$");
  }

  @Override
  public boolean userNameIsValid(String userName) {
    return userName.matches("^[A-Za-z]{5,15}?$");
  }

  @Override
  public boolean passwordIsValid(String password) {
    return password.matches("^[0-9a-zA-Z]{6,18}?$");
  }
}