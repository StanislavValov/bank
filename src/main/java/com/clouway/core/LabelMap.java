package com.clouway.core;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public class LabelMap implements SiteMap {

    @Override
    public String sessionCookieName() {
        return "sid";
    }


    @Override
    public String loginForm() {
        return "/login";
    }

    @Override
    public String transactionErrorLabel() {
        return "/bank/TransactionError.html";
    }

    @Override
    public String registrationForm() {
        return "/registration";
    }

    @Override
    public String logoutController() {
        return "/logout";
    }

    @Override
    public String bankController() {
        return "/bankController";
    }
}