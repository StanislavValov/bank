package com.clouway.core;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public interface SiteMap {

  String sessionCookieName();

  String loginForm();

  String transactionAmountLabel();

  String transactionErrorLabel();

  String registrationForm();

  String logoutController();

  String bankController();
}