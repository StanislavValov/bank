<%--
  Created by IntelliJ IDEA.
  User: clouway
  Date: 5/21/14
  Time: 3:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ page import="com.clouway.bank.Counter" %>--%>

<html>
<head>
    <title></title>
</head>
<body>
<a href="/bank/RegistrationForm.jsp">Registration</a>
<p></p>
<p>Log in:</p>
<form method="post" action="/LoginController">
    Username: <input type="text" name="userName"><br/>
    Password: <input type="password" name="password"><br/>
    <input type="submit" value="Login">
    <%
        if (request.getAttribute("error")!= null) {
            out.println(request.getAttribute("error"));
        }
    %>
    <p>Users online:
    <%
        if (session.getServletContext().getAttribute("counter") != null){
        out.print(session.getServletContext().getAttribute("counter"));
        }
    %>
    </p>
</form>

</body>
</html>
