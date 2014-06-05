package com.clouway.bank.http;

import com.clouway.bank.core.BankService;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public class RegistrationControllerTest {

  RegistrationController reg;
  Mockery context = new JUnit4Mockery();

  HttpServletRequest request = context.mock(HttpServletRequest.class);
  HttpServletResponse response = context.mock(HttpServletResponse.class);
  BankService bankService = context.mock(BankService.class);

  @Before
  public void setUp() throws Exception {
//    reg = new RegistrationController(bank);
  }

  @Test
  public void createAccountSuccess() throws Exception {

    context.checking(new Expectations() {
      {
//        oneOf(bank).registerUser("Torbalan", "unknown");
        oneOf(request).getParameter("userName");
        will(returnValue("Torbalan"));
        oneOf(request).getParameter("password");
        will(returnValue("unknown"));
        oneOf(response).sendRedirect("/bank/Login.jsp");
      }
    });
//    reg.doPost(request, response);
  }

  @Test
  public void createAccountFailed() throws Exception {

    context.checking(new Expectations() {
      {
//        oneOf(bank).registerUser("Torbalan", "unknown");
        will(throwException(new SQLException()));
        oneOf(request).getParameter("userName");
        will(returnValue("Torbalan"));
        oneOf(request).getParameter("password");
        will(returnValue("unknown"));
        oneOf(request).setAttribute("error", "Username already exist!");
        oneOf(request).getRequestDispatcher("/bank/RegistrationForm.jsp");
      }
    });
//    reg.doPost(request, response);
  }
}
