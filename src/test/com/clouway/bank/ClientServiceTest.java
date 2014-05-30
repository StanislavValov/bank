package com.clouway.bank;

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
public class ClientServiceTest {

  ClientService clientService = null;
  Mockery context = new JUnit4Mockery();

  HttpServletRequest request = context.mock(HttpServletRequest.class);
  HttpServletResponse response = context.mock(HttpServletResponse.class);
  HttpSession session = context.mock(HttpSession.class);
  Connection connection = context.mock(Connection.class);

  @Before
  public void setUp() throws Exception {

    clientService = new ClientService(connection);
  }

  @Test
  public void depositSuccess() throws Exception {

    context.checking(new Expectations(){
      {
        oneOf(request).getSession();
        will(returnValue(session));
        oneOf(request).getParameter("amount");
        will(returnValue("10.11"));
        oneOf(request).getParameter("deposit");
        will(returnValue("Deposit"));
        oneOf(session).getAttribute("userName");
        will(returnValue("Ivan"));
        oneOf(request).getParameter("amount");
        will(returnValue("10.12"));
        oneOf(connection).deposit("Ivan", 10.12);
        oneOf(response).sendRedirect("/bank/User.jsp");
        oneOf(request).getParameter("withdraw");
        oneOf(session).getAttribute("userName");
        will(returnValue("Ivan"));
        oneOf(connection).getCurrency("Ivan");
        oneOf(session).setAttribute("currency",0.0);
        oneOf(request).getParameter("logout");
      }
    });
    clientService.doPost(request,response);
  }

  @Test
  public void depositFailed() throws Exception {

  }

  @Test
  public void withdrawSuccess() throws Exception {
    context.checking(new Expectations(){
      {
        oneOf(request).getSession();
        will(returnValue(session));
        oneOf(request).getParameter("amount");
        will(returnValue("11.12"));
        oneOf(request).getParameter("deposit");
        oneOf(request).getParameter("withdraw");
        will(returnValue("Withdraw"));
        oneOf(session).getAttribute("userName");
        will(returnValue("Ivan"));
        oneOf(session).getAttribute("userName");
        will(returnValue("Ivan"));
        oneOf(request).getParameter("amount");
        will(returnValue("10.12"));
        oneOf(response).sendRedirect("/bank/User.jsp");
        oneOf(connection).withdraw("Ivan", 10.12);
        oneOf(connection).getCurrency("Ivan");
        oneOf(session).setAttribute("currency",0.0);
        oneOf(request).getParameter("logout");
      }
    });
    clientService.doPost(request,response);

  }
}
