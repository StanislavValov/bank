package com.clouway.http;

import com.clouway.core.AccountService;
import com.clouway.core.BankValidator;
import com.clouway.core.LabelMap;
import com.clouway.core.SiteMap;
import com.clouway.core.User;
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
public class RegistrationControllerTest {

  Mockery context = new JUnit4Mockery();
  RegistrationController registrationController;
  User user;
  SiteMap siteMap;


  HttpServletRequest request = context.mock(HttpServletRequest.class);
  HttpServletResponse response = context.mock(HttpServletResponse.class);
  AccountService accountService = context.mock(AccountService.class);
  BankValidator bankValidator = context.mock(BankValidator.class);

  @Before
  public void setUp() throws Exception {
    siteMap = new LabelMap();
//    user = new User();
    registrationController = new RegistrationController(accountService, bankValidator, siteMap);
  }

  @Test
  public void createAccountSuccessful() throws Exception {

    context.checking(new Expectations() {
      {
        oneOf(request).getParameter(siteMap.password());
        will(returnValue(user.getPassword()));

        oneOf(request).getParameter(siteMap.userName());
        will(returnValue(user.getUserName()));

        oneOf(bankValidator).isUserCorrect(user);
        will(returnValue(true));

        oneOf(accountService).userExists(user);
        will(returnValue(false));

        oneOf(accountService).registerUser(user);

        oneOf(response).sendRedirect(siteMap.loginJspLabel());

        oneOf(request).getRequestDispatcher(siteMap.registrationJspLabel());
      }
    });
  }

  @Test
  public void validateDataFailed() throws Exception {

    context.checking(new Expectations() {
      {
        oneOf(request).getParameter(siteMap.password());
        will(returnValue(user.getPassword()));

        oneOf(request).getParameter(siteMap.userName());
        will(returnValue(user.getUserName()));

        oneOf(bankValidator).isUserCorrect(user);
        will(returnValue(false));

        oneOf(request).setAttribute(siteMap.errorLabel(), siteMap.validateErrorMessage());

        oneOf(request).getRequestDispatcher(siteMap.registrationJspLabel());
      }
    });
  }

  @Test
  public void userAlreadyExists() throws Exception {

    context.checking(new Expectations() {
      {
        oneOf(request).getParameter(siteMap.password());
        will(returnValue(user.getPassword()));

        oneOf(request).getParameter(siteMap.userName());
        will(returnValue(user.getUserName()));

        oneOf(bankValidator).isUserCorrect(user);
        will(returnValue(true));

        oneOf(accountService).userExists(user);
        will(returnValue(true));

        oneOf(request).setAttribute(siteMap.errorLabel(), siteMap.userExistErrorLabel());

        oneOf(request).getRequestDispatcher(siteMap.registrationJspLabel());
      }
    });
  }
}