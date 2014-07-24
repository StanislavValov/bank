package com.clouway.http;


import com.clouway.core.SessionService;
import com.clouway.core.IdGenerator;
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
    private IdGenerator idGenerator;
    private User user = new User();


    @Inject
    public LoginController(AuthorisationService authorisationService, SessionService sessionService, SiteMap siteMap, IdGenerator idGenerator) {
        this.authorisationService = authorisationService;
        this.sessionService = sessionService;
        this.siteMap = siteMap;
        this.idGenerator = idGenerator;
    }

    @Post
    public String authorise(HttpServletResponse response) {


        if (!authorisationService.isUserAuthorised(user)) {
            return null;
        }

        String sessionId = idGenerator.generateFor(user);

        response.addCookie(new Cookie(siteMap.sessionCookieName(), sessionId));
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