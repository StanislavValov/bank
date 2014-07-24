package com.clouway.http;

import com.clouway.core.*;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
@Singleton
public class SecurityFilter implements Filter {

    private Provider<User> currentUserProvider;
    private SessionService sessionService;
    private SiteMap siteMap;
    private ClockUtil clockUtil;

    @Inject
    public SecurityFilter(Provider<User> currentUserProvider, SessionService sessionService, SiteMap siteMap, ClockUtil clockUtil) {
        this.currentUserProvider = currentUserProvider;
        this.sessionService = sessionService;
        this.siteMap = siteMap;
        this.clockUtil = clockUtil;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletResponse response = (HttpServletResponse) servletResponse;

        User user = currentUserProvider.get();
        Map<String, Date> sessionsExpirationTime = sessionService.getSessionsExpirationTime();
        Date currentTime = clockUtil.currentTime();

        if (user != null) {

            if (sessionsExpirationTime.get(user.getSessionId()).before(currentTime)) {

                response.sendRedirect(siteMap.logoutController());

            } else {
                sessionService.resetSessionLife(user.getSessionId());
                filterChain.doFilter(servletRequest, servletResponse);
            }

        } else {
            response.sendRedirect(siteMap.loginForm());
        }

        for (String sessionId : sessionsExpirationTime.keySet()) {
            if (sessionsExpirationTime.get(sessionId).before(currentTime)) {
                sessionService.removeSession(sessionId);
            }
        }
    }

    @Override
    public void destroy() {

    }
}