<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ attribute name="multiple" required="false" %>

<div class="form-input">
    <form:label path="files">Upload Image</form:label>
    <form:input type="file" accept="image/*" path="files" multiple="${multiple}"/>
    <form:errors path="files"/>
</div>