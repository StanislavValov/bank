package com.clouway.http;

import com.clouway.core.*;
import com.clouway.core.SessionService;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.sitebricks.At;
import com.google.sitebricks.Show;
import com.google.sitebricks.http.Post;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
@At("/logout")
@Show("/bank/Login.html")
@Singleton
public class LogoutController {

    private Provider<CurrentUser> currentUserProvider;
    private SessionService sessionService;
    private SiteMap siteMap;

    @Inject
    public LogoutController(Provider<CurrentUser> currentUserProvider, SessionService sessionService, SiteMap siteMap) {
        this.currentUserProvider = currentUserProvider;
        this.sessionService = sessionService;
        this.siteMap = siteMap;
    }

    @Post
    public String logout(HttpServletRequest req, HttpServletResponse resp) {

        Cookie[] cookies = req.getCookies();

        for (Cookie cookie : cookies) {

            User currentUser = currentUserProvider.get().getUser();

            if (currentUser.getSessionId().equalsIgnoreCase(cookie.getValue())) {
                cookie.setMaxAge(0);
                sessionService.removeSession(currentUser.getSessionId());
                resp.addCookie(cookie);
            }
        }
        return siteMap.loginForm();
    }
}