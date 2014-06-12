package com.clouway.bank.http;

import com.clouway.bank.core.*;
import com.clouway.bank.persistence.PersistentBankService;
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
    serve("/Registration.do").with(RegistrationController.class);
    serve("/UserAccountController.do").with(UserAccountController.class);
    serve("/LogoutController.do").with(LogoutController.class);

    bind(BankService.class).to(PersistentBankService.class);
    bind(AccountService.class).to(PersistentBankService.class);
    bind(Security.class).to(SessionIdGenerator.class);
    bind(BankValidator.class).to(Validator.class);
  }

  @Provides
  public Connection getConnection() {
    return ConnectionPerRequestFilter.CONNECTION.get();
  }

  @Provides
  @RequestScoped
  public CurrentUser getCurrentUser(Provider<HttpServletRequest> requestProvider, AccountService accountService) {
    Cookie[] cookies = requestProvider.get().getCookies();

    if (cookies!=null) {
      for (Cookie cookie : cookies) {

        if (cookie.getValue().equalsIgnoreCase(accountService.getSessionId(cookie.getName()))) {

          User user = accountService.findUserAssociatedWithSession(cookie.getValue());
          return new CurrentUser(user);
        }
      }
    }

    return null;
  }
}