<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ attribute name="items" required="true" type="java.util.List<ar.edu.itba.paw.models.Locations>" %>
<%@ attribute name="path" required="true" %>
<%@ attribute name="labelCode" required="true" %>
<%@ attribute name="errorCode" required="true" %>


<html>
<body>
<div class="form-group">
    <form:label path="${path}"><spring:message code="${labelCode}"/></form:label>
    <form:select path="${path}" class="form-control form-control-custom">
        <c:forEach var="loc" items="${items}">
            <form:option value="${loc.ordinal()}" label="${loc.name}">
                <c:out value="${loc.name}"/>
            </form:option>
        </c:forEach>
    </form:select>
    <form:errors path="${path}" element="p" cssClass="error">
        <spring:message code="${errorCode}"/>
    </form:errors>
</div>
</body>
</html>