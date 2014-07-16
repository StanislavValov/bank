package com.clouway.core;

/**
 * Created by hisazzul@gmail.com on 7/15/14.
 */
public class Error {

    private String userExistErrorMessage;
    private String validationErrorMessage;

    public String getUserExistErrorMessage() {
        return userExistErrorMessage;
    }

    public String getValidationErrorMessage() {
        return validationErrorMessage;
    }

    public void setUserExistErrorMessage(String userExistErrorMessage) {
        this.userExistErrorMessage = userExistErrorMessage;
    }

    public void setValidationErrorMessage(String validationErrorMessage) {
        this.validationErrorMessage = validationErrorMessage;
    }
}
