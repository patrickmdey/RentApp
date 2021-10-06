<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="controls" tagdir="/WEB-INF/tags/Controls" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:url value="/user/updatePassword" var="actionUrl"/>

<html>
<h:head title="Editar contrase;a CAMBIAR"/>

<body class="bg-color-grey">
<h:navbar loggedUser="${user}"/>


<div class="main-container min-height">

    <c:if test="${showPanel}">
        <h:messagePanel mode="success" visible="true" messages="account.updatePassword.form.success"/>
    </c:if>

    <div class="card shadow card-style create-card mx-3">
        <div class="form-container">
            <h3 class="h3 fw-bold my-1"><spring:message code="account.updatePassword.form.title"/></h3>
            <hr/>

            <form:form method="post" action="${actionUrl}" modelAttribute="passwordForm">
                <div class="row">
                    <div class="col-6">
                        <controls:TextBox path="password" type="password"
                                          labelCode="account.form.password"
                                          placeholderCode="placeholder.password"/>
                    </div>
                    <div class="col-6">
                        <controls:TextBox path="confirmPassword" type="password"
                                          labelCode="account.form.confirmPassword"
                                          placeholderCode="placeholder.secondPassword"
                        />
                    </div>
                </div>
                <div class="row mt-3">
                    <div class="col-6">
                        <c:url value="/user/view" var="cancelUrl"/>
                        <controls:LinkButton col="col-12" href="${cancelUrl}"
                                             color="btn-danger"
                                             labelCode="account.form.cancelButton"/>
                    </div>
                    <div class="col-6">
                        <controls:Button col="col-12" color="bg-color-action btn-dark"
                                         labelCode="account.form.publishButton"/>
                    </div>

                </div>

            </form:form>

        </div>
    </div>
</div>

<h:footer/>

</body>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
<script src="<c:url value="/resources/js/main.js" />" defer></script>
</html>
