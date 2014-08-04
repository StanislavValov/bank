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

    private Provider<Session> currentSession;
    private SessionService sessionService;
    private SiteMap siteMap;

    @Inject
    public LogoutController(Provider<Session> currentSession, SessionService sessionService, SiteMap siteMap) {
        this.currentSession = currentSession;
        this.sessionService = sessionService;
        this.siteMap = siteMap;
    }

    @Post
    public String logout() {

        Session session = currentSession.get();

        sessionService.remove(session.getId());

        return siteMap.loginForm();
    }
}