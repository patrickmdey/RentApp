<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ attribute name="path" required="true" %>
<%@ attribute name="type" required="true" %>
<%@ attribute name="labelCode" required="true" %>

<html>
    <body>
    <div class="form-group">
        <form:label path="${path}"><spring:message code="${labelCode}"/></form:label>
        <form:input path="${path}" type="${type}" class="form-control form-control-custom"/>
        <form:errors path="${path}" element="p" cssClass="text-danger"/>
    </div>
    </body>
</html>