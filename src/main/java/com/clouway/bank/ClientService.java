package com.clouway.bank;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */

@Singleton
public class ClientService extends HttpServlet {

  Connection connection;

  @Inject
  public ClientService(Connection connection) {
    this.connection = connection;
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    HttpSession session = req.getSession();
    Validator validator = new Validator();

    if (validator.transactionValidator(req.getParameter("amount"))) {
      if ("Deposit".equals(req.getParameter("deposit"))) {
        connection.deposit(String.valueOf(session.getAttribute("userName")),
                ((Double.parseDouble(req.getParameter("amount")))));
        resp.sendRedirect("/bank/User.jsp");
      }
      if ("Withdraw".equals(req.getParameter("withdraw"))) {
        connection.withdraw(String.valueOf(session.getAttribute("userName")),
                ((Double.parseDouble(req.getParameter("amount")))));
        resp.sendRedirect("/bank/User.jsp");
      }
    }
    else {
      session.setAttribute("invalidAmount","Please enter valid amount");
      PrintWriter out = resp.getWriter();
      out.print("<html>");
      out.print("<body>");
      out.print(session.getAttribute("invalidAmount"));
      out.print("<form action='/bank/User.jsp'>");
      out.println("<p><input type='submit' value='Back'></p>");
      out.print("</form>");
      out.print("</body>");
      out.print("</html>");
    }

    session.setAttribute("currency",
            connection.getCurrency(String.valueOf(session.getAttribute("userName"))));

    if ("Logout".equals(req.getParameter("logout"))) {
      session.invalidate();
      resp.sendRedirect("/bank/View.jsp");
    }
  }
}