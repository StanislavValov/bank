package com.clouway.http;

import com.clouway.core.SessionService;
import com.clouway.core.User;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.sitebricks.At;
import com.google.sitebricks.Show;
import com.google.sitebricks.http.Post;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
@At("/login")
@Show("/bank/Login.html")
@Singleton
public class LoginController {

    private AuthorisationService authorisationService;
    private SessionService sessionService;
    private User user = new User();

    @Inject
    public LoginController(AuthorisationService authorisationService, SessionService sessionService) {
        this.authorisationService = authorisationService;
        this.sessionService = sessionService;
    }

    @Post
    public String authorisation(HttpServletResponse response) {

        if (!authorisationService.isUserAuthorised(user)) {
            return null;

        } else {
            HashFunction hf = Hashing.sha1();
            HashCode hashCode = hf.hashString(user.getUserName() + user.getPassword() + System.currentTimeMillis());
            String sessionId = hashCode.toString();
            response.addCookie(new Cookie("sid", sessionId));
            sessionService.addUserAssociatedWithSession(user);
            return "/bankController";
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}