<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="control" tagdir="/WEB-INF/tags/Controls" %>

<html>
<h:head title="Edit profile"/>

<body>
<h:navbar/>

<div class="card container">
    <div class="card-body">
        <div class="row">
                <h3 class="card-title"><spring:message code="account.edit.form.title"/></h3>

        </div>
        <h:account mode="edit"/>
    </div>
</div>

</body>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
<script src="<c:url value="/js/main.js" />" defer></script>
</html>
