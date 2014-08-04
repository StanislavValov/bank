package com.clouway.core;

/**
 * Created by hisazzul@gmail.com on 7/24/14.
 */
public class RegexUserValidator implements UserValidator{

    @Override
    public boolean userIsCorrect(User user) {
        return user.getUserName().matches("^[A-Za-z]{5,15}?$") &&
                user.getPassword().matches("^[0-9a-zA-Z]{6,18}?$");
    }
}