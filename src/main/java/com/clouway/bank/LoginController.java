package com.clouway.bank;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
@Singleton
public class LoginController extends HttpServlet  {

  private Connection connection;

  @Inject
  public LoginController(Connection connection) {
    this.connection = connection;
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    HttpSession session = req.getSession();
    User user = new User();

    user.setUserName(req.getParameter("userName"));
    user.setPassword(req.getParameter("password"));

    if (user.getPassword().equals(connection.getPassword(user.getUserName()))) {
      session.setAttribute("userName", user.getUserName());
      session.setAttribute("currency", connection.getCurrency(user.getUserName()));
      req.getRequestDispatcher("/bank/User.jsp").forward(req, resp);
    } else {
      req.setAttribute("error", "Wrong Password or Username");
      req.getRequestDispatcher("/bank/View.jsp").forward(req, resp);
    }
  }
}