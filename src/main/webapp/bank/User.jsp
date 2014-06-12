<%@ page import="com.clouway.bank.core.User" %>
<%@ page import="java.text.DecimalFormat" %>
<%--
  Created by IntelliJ IDEA.
  User: clouway
  Date: 5/22/14
  Time: 2:57 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <title></title>
</head>
<body bgcolor="#b0c4de">


<Form method="post" action="UserAccountController.do">
    <% DecimalFormat format = new DecimalFormat("#.##");%>
    <p>Amount: <%= format.format(request.getAttribute("amount")) %> $</p>
</Form>

<Form method="post" action="/BankController.do">
    <input type="text" name="amount"><br/>
    <input type="submit" name="deposit" value="Deposit">
    <input type="submit" name="withdraw" value="Withdraw">
</Form>

<p>
    <%
        if (request.getAttribute("error") != null) {
            out.print(request.getAttribute("error"));
        }
        if (session.getAttribute("transactionError") != null) {
            out.print(session.getAttribute("transactionError"));
        }
    %>
</p>

<form method="post" action="/LogoutController.do">
    <input type="submit" name="logout" value="Logout">
</form>

</body>
</html>
