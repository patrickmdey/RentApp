<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="path" required="true" %>
<%@ attribute name="type" required="true" %>
<%@ attribute name="labelCode" required="true" %>
<%@ attribute name="placeholderCode" required="true" %>


<body>
<div class="form-group">
    <form:label path="${path}"><spring:message code="${labelCode}"/></form:label>
    <spring:message code="${placeholderCode}" var="placeholder"/>
    <div class="input-group">
        <form:input path="${path}" type="${type}" placeholder="${placeholder}" class="form-control form-control-custom"/>
        <c:if test="${type == 'password'}">
            <div class="input-group-append">
                <span class="input-group-text"><i class="bi bi-eye-slash"></i></span>
            </div>
        </c:if>
    </div>
    <form:errors path="${path}" cssClass="error" element="p"/>
</div>
</body>
