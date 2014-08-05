package com.clouway.http;

import com.clouway.core.*;
import com.clouway.core.SessionRepository;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.sitebricks.At;
import com.google.sitebricks.Show;
import com.google.sitebricks.http.Post;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
@At("/logout")
@Show("/bank/Login.html")
@Singleton
public class LogoutController {

    private Provider<Session> currentSession;
    private SessionRepository sessionRepository;
    private SiteMap siteMap;

    @Inject
    public LogoutController(Provider<Session> currentSession, SessionRepository sessionRepository, SiteMap siteMap) {
        this.currentSession = currentSession;
        this.sessionRepository = sessionRepository;
        this.siteMap = siteMap;
    }

    @Post
    public String logout() {

        Session session = currentSession.get();

        sessionRepository.remove(session.getId());

        return siteMap.loginForm();
    }
}