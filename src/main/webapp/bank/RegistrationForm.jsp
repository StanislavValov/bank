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
    <title></title>
</head>
<body>

<form method="post" action="/Registration">
    Username: <input type="text" name="userName"><br/>
    Password: <input type="password" name="password"><br/>
    <input type="submit" value="Register">
    <%
        if (request.getAttribute("error")!= null) {
            out.println(request.getAttribute("error"));
        }
    %>
</form>

</body>
</html>
