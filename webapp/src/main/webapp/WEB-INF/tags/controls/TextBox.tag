<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ attribute name="path" required="true" %>
<%@ attribute name="type" required="true" %>
<%@ attribute name="labelCode" required="true" %>
<%@ attribute name="placeholderCode" required="true" %>
<%--<%@ attribute name="errorCode" required="true" %>--%>


<body>
<div class="form-group">
    <form:label path="${path}"><spring:message code="${labelCode}"/></form:label>
    <spring:message code="${placeholderCode}" var="placeholder"/>
    <form:input path="${path}" type="${type}" placeholder="${placeholder}" class="form-control form-control-custom"/>
    <form:errors path="${path}"/>
<%--    <form:errors path="${path}" element="p" cssClass="error">--%>
<%--        <spring:message code="${errorCode}"/>--%>
<%--    </form:errors>--%>
</div>
</body>
