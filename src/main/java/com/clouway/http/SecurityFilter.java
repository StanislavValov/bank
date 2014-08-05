package com.clouway.http;

import com.clouway.core.*;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
@Singleton
public class SecurityFilter implements Filter {

    private Provider<Session> currentSessionProvider;
    private SessionRepository sessionRepository;
    private SiteMap siteMap;

    @Inject
    public SecurityFilter(Provider<Session> currentSessionProvider, SessionRepository sessionRepository, SiteMap siteMap) {
        this.currentSessionProvider = currentSessionProvider;
        this.sessionRepository = sessionRepository;
        this.siteMap = siteMap;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletResponse response = (HttpServletResponse) servletResponse;

        Session currentSession = currentSessionProvider.get();
        Date currentTime = new Timestamp(System.currentTimeMillis());

        if (currentSession != null) {

            if (currentSession.getExpirationDate().before(currentTime)) {

                response.sendRedirect(siteMap.logoutController());

            } else {
                sessionRepository.reset(currentSession.getId());
                filterChain.doFilter(servletRequest, servletResponse);
            }

        } else {
            response.sendRedirect(siteMap.loginForm());
        }
    }

    @Override
    public void destroy() {

    }
}