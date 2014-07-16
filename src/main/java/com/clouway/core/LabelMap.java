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
    public String loginForm() {
        return "/login";
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
    public String transactionErrorLabel() {
        return "/bank/TransactionError.html";
    }

    @Override
    public String userHtmlForm() {
        return "/bank/User.html";
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