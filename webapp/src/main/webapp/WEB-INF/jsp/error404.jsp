<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:url value="/" var="marketplace"/>
<html>
<h:head title="RentApp"/>
<body class="bg-color-grey">
<h:navbar/>
<div class="error-container container-height">
    <h1 class="display-3 fw-bold">Error 404</h1>
    <p class="lead"><spring:message code="error.404"/></p>
    <a href="${marketplace}"><spring:message code="error.return"/></a>

</div>
<h:footer/>
</body>
<h:mainScript/>
</html>