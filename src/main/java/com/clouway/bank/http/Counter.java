package com.clouway.bank.http;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */

public class Counter implements HttpSessionListener {

  private int activeSessions = 0;

  @Override
  public void sessionCreated(HttpSessionEvent httpSessionEvent) {
    activeSessions++;
    httpSessionEvent.getSession().setAttribute("counter",activeSessions);
  }

  @Override
  public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
    if (activeSessions > 0) {
      activeSessions--;
      httpSessionEvent.getSession().setAttribute("counter",activeSessions);
    }
  }
}
