package com.clouway.bank.http;

import com.clouway.bank.core.AccountService;
import com.clouway.bank.core.CurrentUser;
import com.clouway.bank.core.SiteMap;
import com.clouway.bank.core.TimeUtility;
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
  private AccountService accountService;
  private SiteMap siteMap;

  @Inject
  public SecurityFilter(Provider<CurrentUser> currentUserProvider, AccountService accountService, SiteMap siteMap) {
    this.currentUserProvider = currentUserProvider;
    this.accountService = accountService;
    this.siteMap = siteMap;
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

    Timestamp expirationTime = accountService.getExpirationTime(currentUserProvider.get().getUser());

    if (currentUserProvider.get()==null || expirationTime.before(TimeUtility.currentTime())) {
      httpResponse.sendRedirect(siteMap.loginJspLabel());
    }
    else {
      accountService.resetSessionLife(currentUserProvider.get().getUser().getSessionId());
      filterChain.doFilter(servletRequest, servletResponse);
    }
  }

  @Override
  public void destroy() {

  }
}