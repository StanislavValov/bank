package com.clouway.bank;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public class Injection  extends GuiceServletContextListener {

  @Override
  protected Injector getInjector() {
    return Guice.createInjector(new ServletModule() {
      @Override
      protected void configureServlets() {

        serve("/ClientService").with(ClientService.class);
        serve("/LoginController").with(LoginController.class);
        serve("/Registration").with(Registration.class);

        bind(Connection.class).to(DbConnection.class);
      }
    });
  }
}

