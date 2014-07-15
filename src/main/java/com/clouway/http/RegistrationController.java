package com.clouway.http;

import com.clouway.core.AccountService;
import com.clouway.core.BankValidator;
import com.clouway.core.SiteMap;
import com.clouway.core.User;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.sitebricks.At;
import com.google.sitebricks.Show;
import com.google.sitebricks.http.Post;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
@At("/registration")
@Show("/bank/RegistrationForm.html")
@Singleton
public class RegistrationController {

    private AccountService accountService;
    private BankValidator validator;
    private SiteMap siteMap;
    private User user = new User();

    @Inject
    public RegistrationController(AccountService accountService, BankValidator validator, SiteMap siteMap) {
        this.accountService = accountService;
        this.validator = validator;
        this.siteMap = siteMap;
    }


    @Post
    public String register() {

        if (validator.isUserCorrect(user)) {

            if (!accountService.userExists(user)) {
                accountService.registerUser(user);
                return "/bank/Login.html";

            } else {
//                req.setAttribute(siteMap.errorLabel(), siteMap.userExistErrorLabel());
            }

        } else {
//            req.setAttribute(siteMap.errorLabel(), siteMap.validateErrorMessage());
        }
        return "/registration";
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}