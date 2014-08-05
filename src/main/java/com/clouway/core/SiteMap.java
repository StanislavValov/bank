package com.clouway.core;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public interface SiteMap {

  String sessionCookieName();

  String loginForm();

  String transactionError();

  String registrationForm();

  String logoutController();

  String bankController();

    String authenticationError();

    String registrationError();
}