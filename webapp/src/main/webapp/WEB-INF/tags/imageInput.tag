<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ attribute name="multiple" required="false" %>

<div class="form-image-input">
    <form:label path="files">Upload Image</form:label>
    <form:input cssClass="mt-2" type="file" accept="image/*" path="files" multiple="${multiple}"/>
    <form:errors path="files"/>
</div>