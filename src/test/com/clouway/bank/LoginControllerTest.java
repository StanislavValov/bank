package com.clouway.bank;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public class LoginControllerTest {

  LoginController loginController;
  Mockery context = new JUnit4Mockery();

  HttpServletRequest request = context.mock(HttpServletRequest.class);
  HttpServletResponse response = context.mock(HttpServletResponse.class);
  Connection connection = context.mock(Connection.class);
  HttpSession session = context.mock(HttpSession.class);


  @Before
  public void setUp() throws Exception {
    loginController = new LoginController(connection);
  }

  @Test
  public void loginSuccessful() throws IOException, ServletException {

    context.checking(new Expectations() {
      {
        oneOf(request).getSession();
        oneOf(session).getServletContext();
        oneOf(connection).getPassword("Torbalan");
        will(returnValue("unknown"));
        oneOf(request).getParameter("userName");
        will(returnValue("Torbalan"));
        oneOf(request).getParameter("password");
        will(returnValue("unknown"));
        oneOf(connection).getCurrency("Torbalan");
        oneOf(request).getRequestDispatcher("/bank/User.jsp");
      }
    });
    loginController.doPost(request, response);
  }

  @Test
  public void loginFailed() throws Exception {
    context.checking(new Expectations(){
      {
        oneOf(request).getSession();
        oneOf(session).getServletContext();
        oneOf(connection).getPassword("Torbalan");
        oneOf(request).getParameter("userName");
        will(returnValue("Torbalan"));
        oneOf(request).getParameter("password");
        will(returnValue("unknown"));
        oneOf(connection).getCurrency("Torbalan");
        oneOf(request).setAttribute("error", "Wrong Password or Username");
        oneOf(request).getRequestDispatcher("/bank/View.jsp");
      }
    });
    loginController.doPost(request,response);
  }
}