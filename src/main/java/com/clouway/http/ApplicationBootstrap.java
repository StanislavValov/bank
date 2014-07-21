package com.clouway.http;

import com.clouway.persistence.PersistentModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.sitebricks.SitebricksModule;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public class ApplicationBootstrap extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new HttpModule(), new PersistentModule(), new SitebricksModule(){
            @Override
            protected void configureSitebricks() {
                scan(LoginController.class.getPackage());
            }
        });
    }
}