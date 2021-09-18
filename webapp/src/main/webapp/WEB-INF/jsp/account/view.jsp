<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="control" tagdir="/WEB-INF/tags/Controls" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<h:head title="Mi perfil"/>

<body>
<h:navbar loggedUserId="${user.id}"/>



<div class="card container">

    <div class="card-body">
        <div class="row">

            <div class="col-10">
        <h3 class="card-title"><spring:message code="account.view.form.title"/></h3>
            </div>
            <div class="col">
    <control:LinkButton href="/user/edit" col="col-12" color="btn-primary" labelCode="account.view.form.editButton"/>
        </div>
    </div>
    <h:account mode="view"/>
    </div>
</div>
</body>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
<script src="<c:url value="/resources/js/main.js" />" defer></script>
</html>
