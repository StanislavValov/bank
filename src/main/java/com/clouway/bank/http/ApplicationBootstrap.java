package com.clouway.bank.http;

import com.clouway.bank.core.AccountService;
import com.clouway.bank.core.BankService;
import com.clouway.bank.core.BankValidator;
import com.clouway.bank.core.Validator;
import com.clouway.bank.persistence.PersistentBankService;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;

import java.sql.Connection;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public class ApplicationBootstrap extends GuiceServletContextListener {

  @Override
  protected Injector getInjector() {
    return Guice.createInjector(new ServletModule() {
      @Override
      protected void configureServlets() {
        filter("/*").through(ConnectionPerRequestFilter.class);
        serve("/ClientService").with(BankController.class);
        serve("/LoginController").with(LoginController.class);
        serve("/Registration").with(RegistrationController.class);
        serve("/UserAccountController").with(UserAccountController.class);


        bind(BankService.class).to(PersistentBankService.class);
        bind(AccountService.class).to(PersistentBankService.class);
        bind(BankValidator.class).to(Validator.class);
      }

      @Provides
      public Connection getConnection() {
        return ConnectionPerRequestFilter.CONNECTION.get();
      }
    });
  }
}