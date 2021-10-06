<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<h:head title="create"/>
<link rel="stylesheet" href="<c:url value="/resources/css/password.css"/>">
<body class="bg-color-grey">
<h:navbar loggedUser="${user}"/>

<div class="main-container min-height">
    <div class="card shadow card-style create-card mx-3">
        <div class="form-container">
            <h3 class="h3 fw-bold my-1"><spring:message code="account.create.form.title"/></h3>
            <hr/>
            <h:account mode="create" locations="${locations}"/>
        </div>
    </div>
</div>

<h:footer/>

</body>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
<script src="<c:url value="/resources/js/main.js" />" defer></script>
<script src="<c:url value="/resources/js/password.js"/>" defer></script>
</html>
