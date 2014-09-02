package com.clouway.http;

import com.clouway.core.*;
import com.clouway.core.SessionRepository;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.sitebricks.At;
import com.google.sitebricks.Show;
import com.google.sitebricks.headless.Reply;
import com.google.sitebricks.headless.Service;
import com.google.sitebricks.http.Post;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
@At("/logout")
@Service
@Singleton
public class LogoutController {

    private Provider<Session> currentSession;
    private SessionRepository sessionRepository;

    @Inject
    public LogoutController(Provider<Session> currentSession, SessionRepository sessionRepository) {
        this.currentSession = currentSession;
        this.sessionRepository = sessionRepository;
    }

    @Post
    public Reply<?> logout() {

        Session session = currentSession.get();

        sessionRepository.remove(session.getId());

        return Reply.saying().ok();
    }
}