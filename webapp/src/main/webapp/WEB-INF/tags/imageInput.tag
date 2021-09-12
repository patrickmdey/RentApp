<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="">
    <form:label path="file">Upload Image</form:label>
    <form:input type="file" accept="image/*" path="file"/>
    <form:errors path="file"/>
</div>