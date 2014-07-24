package com.clouway.http;

import com.clouway.core.*;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.servlet.RequestScoped;
import com.google.inject.servlet.ServletModule;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public class HttpModule extends ServletModule {

    @Override
    protected void configureServlets() {
        filter("/bankController").through(SecurityFilter.class);

        bind(BankValidator.class).to(Validator.class);
        bind(SiteMap.class).to(LabelMap.class);
        bind(ClockUtil.class).to(Clock.class);
        bind(IdGenerator.class).to(SessionIdGenerator.class);
    }

    @Provides
    @RequestScoped
    public User getCurrentUser(Provider<HttpServletRequest> requestProvider, SessionService sessionService, SiteMap siteMap) {
        Cookie[] cookies = requestProvider.get().getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {

                if (cookie.getName().equalsIgnoreCase(siteMap.sessionCookieName())) {
                    return sessionService.findUserAssociatedWithSession(cookie.getValue());

                }
            }
        }
        return null;
    }
}