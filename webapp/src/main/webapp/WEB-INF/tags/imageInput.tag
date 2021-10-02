<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ attribute name="multiple" required="false" %>
<%@ attribute name="path" required="true" %>

<div class="form-image-input">
    <form:label path="${path}">
        <spring:message code="image.input.label"/>
    </form:label>
    <form:input cssClass="mt-2" type="file" accept="image/*" path="${path}" multiple="${multiple}"/>
    <form:errors path="${path}"/>
</div>