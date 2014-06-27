package com.clouway.http;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public class ApplicationBootstrap extends GuiceServletContextListener {

  @Override
  protected Injector getInjector() {
    return Guice.createInjector(new HttpModule());
  }
}