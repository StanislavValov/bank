package com.clouway.http;

import com.clouway.core.*;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.sitebricks.At;
import com.google.sitebricks.Show;
import com.google.sitebricks.http.Post;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
@At("/registration")
@Show("/bank/RegistrationForm.html")
@Singleton
public class RegistrationController {

    private UserRepository userRepository;
    private UserValidator validator;
    private SiteMap siteMap;
    private User user = new User();

    @Inject
    public RegistrationController(UserRepository userRepository, UserValidator validator, SiteMap siteMap) {
        this.userRepository = userRepository;
        this.validator = validator;
        this.siteMap = siteMap;
    }

    @Post
    public String register() {

        if (validator.userIsCorrect(user)) {

            if (!userRepository.exists(user.getUserName())) {
                userRepository.register(user);
                return siteMap.loginForm();
            }
        }
        return siteMap.registrationError();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}