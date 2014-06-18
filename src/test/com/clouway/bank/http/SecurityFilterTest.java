package com.clouway.bank.http;

import com.clouway.bank.core.AccountService;
import com.clouway.bank.core.LabelMap;
import com.clouway.bank.core.SiteMap;
import com.clouway.bank.core.User;
import com.google.inject.Provider;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public class SecurityFilterTest {

  Mockery context = new JUnit4Mockery();
  SecurityFilter securityFilter;
  SiteMap siteMap;
  User user;

  ServletRequest request = context.mock(ServletRequest.class);
  ServletResponse response = context.mock(ServletResponse.class);
  FilterChain filterChain = context.mock(FilterChain.class);
  AccountService accountService = context.mock(AccountService.class);
  Provider provider = context.mock(Provider.class);

  @Before
  public void setUp() throws Exception {
    user = new User("Torbalan","unknown",null,"111");
    siteMap = new LabelMap();
    securityFilter = new SecurityFilter(provider,accountService,siteMap);
  }

  @Test
  public void sessionExpired() throws Exception {
    context.checking(new Expectations(){
      {
        oneOf(accountService).getExpirationTime(user);
      }
    });
    securityFilter.doFilter(request,response,filterChain);
  }
}
