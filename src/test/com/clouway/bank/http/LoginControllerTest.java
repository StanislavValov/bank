package com.clouway.bank.http;

import com.clouway.bank.core.AccountService;
import com.clouway.bank.core.User;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public class LoginControllerTest {

  LoginController loginController;
  Mockery context = new JUnit4Mockery();
  User user;

  HttpServletRequest request = context.mock(HttpServletRequest.class);
  HttpServletResponse response = context.mock(HttpServletResponse.class);
  AccountService accountService = context.mock(AccountService.class);
  HttpSession session = context.mock(HttpSession.class);

  @Before
  public void setUp() throws Exception {
//    user = new User();
//    loginController = new LoginController(accountService, user);
  }

  @Test
  public void loginSuccess() throws Exception {
//    user.setUserName("Ivan");
//    user.setPassword("unknown");

    context.checking(new Expectations() {
      {
        oneOf(request).getParameter("userName");
        will(returnValue(user.getUserName()));

        oneOf(request).getParameter("password");
        will(returnValue(user.getPassword()));

        oneOf(request).getSession();
        will(returnValue(session));

//        oneOf(accountService).getPassword(user);
        will(returnValue(user.getPassword()));

        oneOf(session).setAttribute("userName", user.getUserName());

//        oneOf(accountService).getAccountAmount(user);
        will(returnValue(11.11));

        oneOf(session).setAttribute("amount", 11.11);
        oneOf(request).getRequestDispatcher("/UserAccountController");
      }
    });
    loginController.doPost(request, response);
  }

  @Test
  public void loginFailed() throws Exception {

//    user.setUserName("Torbalan");
//    user.setPassword("unknown");

    context.checking(new Expectations(){
      {
        oneOf(request).getSession();
        will(returnValue(session));

        oneOf(request).getParameter("userName");
        will(returnValue(user.getUserName()));

        oneOf(request).getParameter("password");
        will(returnValue(user.getPassword()));

//        oneOf(accountService).getPassword(user);
        will(returnValue("somethingElse"));

        oneOf(session).setAttribute("error","Wrong Password or Username");

        oneOf(response).sendRedirect("/bank/Login.jsp");
      }
    });
    loginController.doPost(request,response);
  }
}