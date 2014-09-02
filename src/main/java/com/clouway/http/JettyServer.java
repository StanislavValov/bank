/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
package com.clouway.http;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * Stanislav
 */
public class JettyServer {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);

        WebAppContext webapp = new WebAppContext();
        webapp.setContextPath("/");
        webapp.setWar("src/main/webapp");
        server.setHandler(webapp);

        server.start();
        server.join();
    }
}