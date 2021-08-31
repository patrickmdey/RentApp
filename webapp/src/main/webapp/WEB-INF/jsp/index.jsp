<%--
  Created by IntelliJ IDEA.
  User: Tatu
  Date: 22/08/2021
  Time: 11:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h2>hello ${currentUser.email}!</h2>
    <p>Your user id is ${currentUser.id}</p>
</body>
</html>
