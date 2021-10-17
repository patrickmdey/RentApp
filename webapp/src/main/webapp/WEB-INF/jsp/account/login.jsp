<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="control" tagdir="/WEB-INF/tags/controls" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<c:url value="/user/login" var="loginUrl"/>
<c:url value="/user/register" var="registerUrl"/>

<html>
<h:head title="title.login"/>
<link rel="stylesheet" href="<c:url value="/resources/css/password.css"/>">
<body class="bg-color-grey">
<h:navbar loggedUser="${user}"/>
<div class="main-container min-height">

    <c:if test="${loginError}">
        <h:messagePanel mode="error" cssClass="create-card card-style " visible="true" messages="login.form.error"/>
    </c:if>


    <div class="card shadow card-style create-card mx-3">
        <form:form method="post" action="${loginUrl}">
            <div class="form-container">
                <h3 class="h3 fw-bold my-1"><spring:message code="login.form.title"/></h3>
                <hr/>
                <div class="row">
                    <div class="form-group">
                        <label for="email"><spring:message code="login.form.email"/></label>
                        <spring:message code="placeholder.email" var="emailPlaceholder"/>
                        <input id="email" name="email" type="text" placeholder="${emailPlaceholder}"
                               class="form-control form-control-custom"/>
                    </div>
                </div>

                <div class="row">
                    <div class="form-group">
                        <label for="password"><spring:message code="login.form.password"/></label>
                        <spring:message code="placeholder.password" var="passwordPlaceholder"/>
                        <div class="input-group">
                            <input id="password" name="password" type="password" placeholder="${passwordPlaceholder}"
                                   class="form-control form-control-custom"/>
                            <div class="input-group-append">
                                <span class="input-group-text"><i class="bi bi-eye-slash"></i></span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <label>
                        <input type="checkbox" name="rememberMe"/>
                        <spring:message code="login.form.rememberMe"/>
                    </label>
                </div>
                <div class="text-center">
                    <control:Button color="bg-color-action btn-dark" labelCode="login.form.loginButton"/>

                    <a class="register-link" href="${registerUrl}">
                        <p class="mt-3">
                            <spring:message code="login.form.registerButton"/>
                        </p>
                    </a>
                </div>
            </div>
        </form:form>
    </div>
</div>
<h:footer/>
</body>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
<script src="<c:url value="/resources/js/main.js" />" defer></script>
<script src="<c:url value="/resources/js/password.js" />" defer></script>

</html>
