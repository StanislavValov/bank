package com.clouway.bank.http;

import com.clouway.bank.core.CurrentUser;
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

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
@Singleton
public class SecurityFilter implements Filter {

  private Provider<CurrentUser> currentUserProvider;

  @Inject
  public SecurityFilter(Provider<CurrentUser> currentUserProvider) {
    this.currentUserProvider = currentUserProvider;
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

    if (currentUserProvider.get()==null) {
      httpResponse.sendRedirect("/bank/Login.jsp");
    }
    else {
      filterChain.doFilter(servletRequest, servletResponse);
    }
  }

  @Override
  public void destroy() {

  }
}