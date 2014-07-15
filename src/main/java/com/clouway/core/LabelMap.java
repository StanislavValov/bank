package com.clouway.core;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public class LabelMap implements SiteMap {

  @Override
  public String cookieName() {
    return "sid";
  }

  @Override
  public String password() {
    return "password";
  }

  @Override
  public String userName() {
    return "userName";
  }

  @Override
  public String userAccountController() {
    return "/UserAccountController.do";
  }

  @Override
  public String identificationFailed() {
    return "Wrong Password or Username";
  }

  @Override
  public String errorLabel() {
    return "error";
  }

  @Override
  public String loginJspLabel() {
    return "/bank/Login.html";
  }

  @Override
  public String depositLabel() {
    return "deposit";
  }

  @Override
  public String withdrawLabel() {
    return "withdraw";
  }

  @Override
  public String transactionAmountLabel() {
    return "amount";
  }

  @Override
  public String successfulTransactionLabel() {
    return "/bank/SuccessfulTransaction.html";
  }

  @Override
  public String amountErrorLabel() {
    return "amountError";
  }

  @Override
  public String amountErrorMessage() {
    return "Please enter valid amount";
  }

  @Override
  public String transactionErrorLabel() {
    return "/bank/TransactionError.html";
  }

  @Override
  public String userJspLabel() {
    return "/bank/User.html";
  }

  @Override
  public String userExistErrorLabel() {
    return "Username already exist";
  }

  @Override
  public String validateErrorMessage() {
    return "Please enter valid Username and password";
  }

  @Override
  public String registrationJspLabel() {
    return "/bank/RegistrationForm.html";
  }

  @Override
  public String logoutController() {
    return "/LogoutController.do";
  }

}