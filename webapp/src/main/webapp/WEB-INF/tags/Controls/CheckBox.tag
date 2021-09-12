<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ attribute name="path" required="true" %>
<%@ attribute name="labelCode" required="true" %>

<html>
    <body>
    <div class="form-group ">
        <div class="form-check">
            <form:checkbox path="${path}" cssClass="form-check-input"/>
            <form:label path="${path}" class="form-check-label"><spring:message code="${labelCode}"/></form:label>
            <form:errors path="${path}" element="p" cssClass="text-danger"/>

        </div>
    </div>

    </body>
</html>