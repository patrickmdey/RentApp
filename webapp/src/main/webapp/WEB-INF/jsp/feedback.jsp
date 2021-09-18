<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>
<html>
<h:head title="Feedback"/>
<body>
<h:navbar loggedUserId="${user.id}"/>
<h1 class="h1 justify">
    <spring:message code="feedback.message"/>
</h1>
</body>
</html>
