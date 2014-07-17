package com.clouway.http;

import com.clouway.core.Generator;
import com.clouway.core.SessionService;
import com.clouway.core.SiteMap;
import com.clouway.core.User;
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
    private SiteMap siteMap;
    private Generator generator;
    private User user = new User();

    @Inject
    public LoginController(AuthorisationService authorisationService, SessionService sessionService, SiteMap siteMap, Generator generator) {
        this.authorisationService = authorisationService;
        this.sessionService = sessionService;
        this.siteMap = siteMap;
        this.generator = generator;
    }

    @Post
    public String authorise(HttpServletResponse response) {

        String sessionId = generator.getUniqueId(user);

        if (!authorisationService.isUserAuthorised(user)) {
            return null;
        }

        response.addCookie(new Cookie(siteMap.cookieName(), sessionId));
        sessionService.addUserAssociatedWithSession(user, sessionId);
        return siteMap.bankController();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}