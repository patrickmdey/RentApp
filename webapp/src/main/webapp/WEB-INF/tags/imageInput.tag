<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ attribute name="multiple" required="false" %>
<%@ attribute name="path" required="true" %>

<div class="form-image-input">
    <form:label path="${path}">Upload Image</form:label>
    <form:input cssClass="mt-2" type="file" accept="image/*" path="${path}" multiple="${multiple}"/>
    <form:errors path="${path}"/>
</div>