package com.clouway.bank.http;

import com.clouway.bank.core.ClockUtil;
import com.clouway.bank.core.CurrentUser;
import com.clouway.bank.core.SiteMap;
import com.clouway.bank.core.User;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

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

    HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

    User user = currentUserProvider.get().getUser();

    if (user != null) {
      Timestamp expirationTime = sessionService.getSessionExpirationTime(user.getSessionId());

      if (expirationTime.before(clockUtil.currentTime())) {

        servletRequest.getRequestDispatcher(siteMap.logoutController()).forward(servletRequest, httpResponse);

      } else {

        sessionService.resetSessionLife(user.getSessionId());
        filterChain.doFilter(servletRequest, servletResponse);
      }
    } else {
      httpResponse.sendRedirect(siteMap.loginJspLabel());
    }
  }

  @Override
  public void destroy() {

  }
}