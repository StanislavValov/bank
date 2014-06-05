<%--
  Created by IntelliJ IDEA.
  User: clouway
  Date: 5/23/14
  Time: 2:29 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>BankRegistration</title>
</head>
<body bgcolor="#b0c4de">

<form method="post" action="/Registration">
    Username: <input type="text" name="userName"><br/>
    Password: <input type="password" name="password"><br/>
    <input type="submit" value="Register">
    <label style="font-style: italic; font-size: small;color: red">
        <%
            if (request.getAttribute("error") != null) {
                out.println(request.getAttribute("error"));
            }
        %>
    </label>
</form>

</body>
</html>
