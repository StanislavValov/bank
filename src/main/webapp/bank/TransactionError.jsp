<%--
  Created by IntelliJ IDEA.
  User: clouway
  Date: 6/3/14
  Time: 9:39 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body bgcolor="#b0c4de">
<label style="font-style: italic; font-size: small;color: red">
    <%= session.getAttribute("invalidAmount") %>
</label>
<p></p>

<form action="/bank/User.jsp">
    <input type="submit" value="Back">
</form>

</body>
</html>
