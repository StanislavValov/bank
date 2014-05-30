package com.clouway.bank;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */

@Singleton
public class Registration extends HttpServlet {

  private Connection connection;

  @Inject
  public Registration(Connection connection) {
    this.connection = connection;
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    try {
      connection.createAccount(req.getParameter("userName"), req.getParameter("password"));
      resp.sendRedirect("/bank/View.jsp");
    } catch (SQLException e) {
      req.setAttribute("error", "Username already exist!");
      req.getRequestDispatcher("/bank/RegistrationForm.jsp").forward(req, resp);
    }
  }
}
