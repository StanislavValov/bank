package com.clouway.http;

import com.clouway.core.AccountService;
import com.clouway.core.LabelMap;
import com.clouway.core.SiteMap;
import com.clouway.core.User;
import com.google.inject.Provider;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public class UserAccountControllerTest {

  Mockery context = new JUnit4Mockery();
  UserAccountController userAccountController;
  SiteMap siteMap;
  User user;

  HttpServletRequest request = context.mock(HttpServletRequest.class);
  HttpServletResponse response = context.mock(HttpServletResponse.class);
  AccountService accountService = context.mock(AccountService.class);
  Provider provider = context.mock(Provider.class);

  @Before
  public void setUp() throws Exception {
    user = new User("Torbalan");
    siteMap = new LabelMap();
    userAccountController = new UserAccountController(accountService, siteMap, provider);
  }

  @Test
  public void redirectToUserJsp() throws Exception {

    context.checking(new Expectations() {
      {
        oneOf(request).getAttribute(siteMap.userName());
        will(returnValue(user.getUserName()));

        oneOf(accountService).getAccountAmount(user);
        will(returnValue(100.00));

        oneOf(request).setAttribute(siteMap.transactionAmountLabel(), 100.00);

        oneOf(request).getRequestDispatcher(siteMap.userJspLabel());
      }
    });
    userAccountController.doPost(request, response);
  }
}
