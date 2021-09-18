<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ attribute name="path" required="true" %>
<%@ attribute name="labelCode" required="true" %>

<html>
<body>
<div class="form-group ">
    <div class="form-check">
        <label>
            <form:checkbox path="${path}" cssClass="form-check-input"/>
            <spring:message code="${labelCode}"/>
        </label>
        <form:errors path="${path}" element="p" cssClass="text-danger"/>
    </div>
</div>

</body>
</html>