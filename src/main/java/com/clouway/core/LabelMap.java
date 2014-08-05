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
    public String transactionError() {
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

    @Override
    public String authenticationError(){
        return "/bank/AuthenticationError.html";
    }

    @Override
    public String registrationError(){
        return "/bank/RegistrationError.html";
    }
}