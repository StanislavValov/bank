package com.clouway.core;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public interface SiteMap {

  String cookieName();

  String password();

  String userName();

  String loginForm();


  String depositLabel();

  String withdrawLabel();

  String transactionAmountLabel();

  String transactionErrorLabel();


  String userHtmlForm();

  String registrationForm();

  String logoutController();

  String bankController();
}