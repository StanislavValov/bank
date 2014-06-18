<%--
  Created by IntelliJ IDEA.
  User: clouway
  Date: 5/21/14
  Time: 3:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ page import="com.clouway.bank.http.Counter" %>--%>

<html>
<head>
    <title>StanBank</title>
</head>
<body bgcolor="#b0c4de">
<a href="/bank/RegistrationForm.jsp">Registration</a>

<p></p>

<p>Log in:</p>

<form method="post" action="/LoginController">
    Username: <input type="text" name="userName"><br/>
    Password: <input type="password" name="password"><br/>
    <input type="submit" value="Login">
    <label style="font-style: italic; font-size: small;color: red">
        <%
            if (request.getAttribute("error") != null) {
                out.println(request.getAttribute("error"));
            }
        %>
    </label>

    <p>Users online:
        <%
            if (session.getAttribute("counter") != null) {
                out.print(session.getAttribute("counter"));
            }
        %>
    </p>
</form>

</body>
</html>
