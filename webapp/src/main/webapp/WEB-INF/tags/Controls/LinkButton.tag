<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="col" required="false" description="default value: col-12"  %>
<%@ attribute name="color" required="false" description="default value: btn-primary"  %>
<%@ attribute name="href" required="true" %>
<%@ attribute name="labelCode" required="true" %>

<c:if test="${empty col}" >
    <c:set var="col" value="col-12" />
</c:if>

<c:if test="${empty color}" >
    <c:set var="color" value="btn-primary" />
</c:if>

<c:url value="${href}" var="url"/>

<html>
<body>
<a href="${url}" class="rounded ${col} btn btn-block ${color}">
    <spring:message  code="${labelCode}"/>
</a>
</body>
</html>