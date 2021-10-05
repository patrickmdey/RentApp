<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:url value="/" var="marketplace"/>
<html>
<h:head title="RentApp"/>
<body class="bg-color-grey">
<h:navbar loggedUser="${user}"/>
<h:errorView errorNum="404" errorMsg="error.404"/>
<h:footer/>
</body>
<h:mainScript/>
</html>