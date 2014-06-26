package com.clouway.bank.core;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public interface SiteMap {

  String cookieName();

  String password();

  String userName();

  String userAccountController();

  String identificationFailed();

  String errorLabel();

  String loginJspLabel();


  String depositLabel();

  String withdrawLabel();

  String transactionAmountLabel();

  String successfulTransactionLabel();

  String amountErrorLabel();

  String amountErrorMessage();

  String transactionErrorLabel();


  String userJspLabel();

  String userExistErrorLabel();

  String validateErrorMessage();

  String registrationJspLabel();

  String logoutController();
}
