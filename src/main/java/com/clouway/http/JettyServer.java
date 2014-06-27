/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
package com.clouway.http;

import org.apache.jasper.servlet.JspServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * Tsony Tsonev tsony_tsonev@abv.bg
 */
public class JettyServer {
  public static void main(String[] args) throws Exception {
    Server server = new Server(8080);

    ServletHolder jspSH = new ServletHolder(JspServlet.class);
    jspSH.setInitParameter("trimSpaces", "true");
    jspSH.setInitParameter("mappedFile", "true");
    jspSH.setInitParameter("classdebuginfo", "true");
    jspSH.setInitParameter("keepGenerated", "true");
    jspSH.setInitParameter("development", "true");
    jspSH.setInitParameter("scratchDir", "./target/scratch");


    WebAppContext webapp = new WebAppContext();
    webapp.setContextPath("/");
    webapp.setWar("src/main/webapp");
    webapp.addServlet(jspSH, "*.jsp");
    server.setHandler(webapp);

    server.start();
    server.join();
  }
}
