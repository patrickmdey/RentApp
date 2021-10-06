<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<c:url value="/" var="marketplace"/>
<html>
<h:head title="title.error.500"/>
<body class="bg-color-grey">
<h:navbar loggedUser="${user}"/>
<h:errorView errorNum="500" errorMsg="${message}"/>
<h:footer/>
</body>
<h:mainScript/>
</html>