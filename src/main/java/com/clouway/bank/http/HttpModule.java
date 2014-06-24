package com.clouway.bank.http;

import com.clouway.bank.core.*;
import com.clouway.bank.persistence.PersistentAccountService;
import com.clouway.bank.persistence.PersistentBankService;
import com.clouway.bank.persistence.PersistentSessionService;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.servlet.RequestScoped;
import com.google.inject.servlet.ServletModule;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public class HttpModule extends ServletModule {

  @Override
  protected void configureServlets() {
    filter("/*").through(ConnectionPerRequestFilter.class);
    filter("*.do").through(SecurityFilter.class);

    serve("/BankController.do").with(BankController.class);
    serve("/LoginController").with(LoginController.class);
    serve("/Registration").with(RegistrationController.class);
    serve("/UserAccountController.do").with(UserAccountController.class);
    serve("/LogoutController.do").with(LogoutController.class);

    bind(BankService.class).to(PersistentBankService.class);
    bind(AccountService.class).to(PersistentAccountService.class);
    bind(SessionService.class).to(PersistentSessionService.class);
    bind(Session.class).to(SessionGenerator.class);
    bind(BankValidator.class).to(Validator.class);
    bind(SiteMap.class).to(LabelMap.class);
    bind(ClockUtil.class).to(Clock.class);
  }

  @Provides
  public Connection getConnection() {
    return ConnectionPerRequestFilter.CONNECTION.get();
  }

  @Provides
  @RequestScoped
  public CurrentUser getCurrentUser(Provider<HttpServletRequest> requestProvider, SessionService sessionService) {
    Cookie[] cookies = requestProvider.get().getCookies();

    if (cookies!=null) {
      for (Cookie cookie : cookies) {
        for (int i = 0; i < sessionService.getSessionIds(cookie.getName()).size(); i++) {

          if (cookie.getValue().equalsIgnoreCase((String) sessionService.getSessionIds(cookie.getName()).get(i))) {

            User user = sessionService.findUserAssociatedWithSession(cookie.getName());
            return new CurrentUser(user);
          }
        }
      }
    }
    return new CurrentUser(null);
  }
}