<%@ page import="com.clouway.bank.User" %>
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
<body>

    <Form method="post" action="/ClientService">
        <input type="text" name="amount"><br/>
        <input type="submit" name="deposit" value="Deposit">
        <input type="submit" name="withdraw" value="Withdraw">
        <input type="submit" name="logout" value="Logout">
    </Form>
    <% DecimalFormat format = new DecimalFormat("#.##");%>
    <p>Currency: <%= format.format(session.getAttribute("currency")) %> $</p>
    <p>
        <%
            if (request.getAttribute("error")!= null){
                out.print(request.getAttribute("error"));
            }
            if (session.getAttribute("transactionError")!=null){
                out.print(session.getAttribute("transactionError"));
            }
        %>
    </p>

</body>
</html>
