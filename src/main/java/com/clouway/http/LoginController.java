package com.clouway.http;

import com.clouway.core.*;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.sitebricks.At;
import com.google.sitebricks.client.transport.Json;
import com.google.sitebricks.headless.Reply;
import com.google.sitebricks.headless.Request;
import com.google.sitebricks.headless.Service;
import com.google.sitebricks.http.Post;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
@At("/login")
@Service
@Singleton
public class LoginController {

    private AuthorisationService authorisationService;
    private SessionRepository sessionRepository;
    private IdGenerator idGenerator;
    private SiteMap siteMap;

    @Inject
    public LoginController(AuthorisationService authorisationService, SessionRepository sessionRepository, IdGenerator idGenerator, SiteMap siteMap) {
        this.authorisationService = authorisationService;
        this.sessionRepository = sessionRepository;
        this.idGenerator = idGenerator;
        this.siteMap = siteMap;
    }

    @Post
    public Reply<?> authorise(Request request, HttpServletResponse response) {

        User user = request.read(User.class).as(Json.class);

        if (!authorisationService.isAuthorised(user)) {
            return Reply.saying().error();
        }

        String sessionId = idGenerator.generateFor(user);
        response.addCookie(new Cookie(siteMap.sessionCookieName(), sessionId));
        sessionRepository.addUser(user, sessionId);

        return Reply.saying().ok();
    }
}