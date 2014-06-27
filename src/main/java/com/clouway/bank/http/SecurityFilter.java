package com.clouway.bank.http;

import com.clouway.bank.core.ClockUtil;
import com.clouway.bank.core.CurrentUser;
import com.clouway.bank.core.SiteMap;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Map;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
@Singleton
public class SecurityFilter implements Filter {

  private Provider<CurrentUser> currentUserProvider;
  private SessionService sessionService;
  private SiteMap siteMap;
  private ClockUtil clockUtil;

  @Inject
  public SecurityFilter(Provider<CurrentUser> currentUserProvider, SessionService sessionService, SiteMap siteMap, ClockUtil clockUtil) {
    this.currentUserProvider = currentUserProvider;
    this.sessionService = sessionService;
    this.siteMap = siteMap;
    this.clockUtil = clockUtil;
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    Map<String, Timestamp> expirationTime = sessionService.getSessionsExpirationTime();

    for (String session : expirationTime.keySet()) {
      if (expirationTime.get(session).before(clockUtil.currentTime())) {
        sessionService.removeSessionId(session);

      } else {
        sessionService.resetSessionLife(session);
      }

      filterChain.doFilter(servletRequest, servletResponse);
    }
  }

  @Override
  public void destroy() {

  }
}