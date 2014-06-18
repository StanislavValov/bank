package com.clouway.bank.core;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public interface SiteMap {

  String password();

  String userName();

  String userAccountController();

  String identificationFailed();

  String errorLabel();

  String loginJspLabel();


  String depositLabel();

  String withdrawLabel();

  String amountLabel();

  String successfulTransactionLabel();

  String amountErrorLabel();

  String amountErrorMessage();

  String transactionErrorLabel();


  String userJspLabel();

  String userExistErrorLabel();

  String validateErrorMessage();

  String registrationJspLabel();
}
