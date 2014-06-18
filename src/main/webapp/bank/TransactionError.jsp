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
    <%= request.getAttribute("amountError") %>
</label>
<p></p>

<form action="/UserAccountController.do">
    <input type="submit" value="Back">
</form>

</body>
</html>
