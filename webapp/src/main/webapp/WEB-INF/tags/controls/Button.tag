
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="labelCode" required="true" %>
<%@ attribute name="col" required="false" description="default value: col-12"  %>
<%@ attribute name="color" required="false" description="default value: btn-primary"  %>

<c:if test="${empty col}" >
    <c:set var="col" value="col-12" />
</c:if>


<c:if test="${empty color}" >
    <c:set var="color" value="btn-primary" />
</c:if>


<html>
    <body>
        <button type="submit" class="${col} rounded btn btn-block ${color}">
            <spring:message code="${labelCode}"/>
        </button>
    </body>
</html>