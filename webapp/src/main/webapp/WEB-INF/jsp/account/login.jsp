<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="control" tagdir="/WEB-INF/tags/Controls" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<c:url value="/user/login" var="loginUrl"/>
<c:url value="/user/register" var="registerUrl"/>

<html>
<h:head title="LogIn"/>
<body class="bg-color-grey">
<h:navbar/>
<div class="main-container">
    <div class="card shadow card-style create-card mx-3">
        <form:form method="post" action="${loginUrl}">
            <div class="form-container">
                <h3 class="h3 fw-bold my-2"><spring:message code="login.form.title"/></h3>
                <hr/>
                <div class="form-group">
                    <label><spring:message code="login.form.email"/></label>
                    <input name="email" type="text" class="form-control form-control-custom"/>
                </div>

                    <%--            <h:TextBox path="email" type="text" labelCode="login.form.email"/>--%>

                <div class="row">
                    <div class="form-group">
                        <label><spring:message code="login.form.password"/></label>
                        <input name="password" type="password" class="form-control form-control-custom"/>
                    </div>
                        <%--            <h:TextBox path="password" type="password" labelCode="login.form.password"/>--%>
                </div>
                <div class="row" style="justify-content: space-around">
                    <control:Button col="col-4" labelCode="login.form.loginButton"></control:Button>

                    <a href="${registerUrl}" class="rounded col-4 btn btn-block  btn-secondary">
                        <spring:message code="login.form.registerButton"/>
                    </a>
                </div>
            </div>
        </form:form>
    </div>
</div>


</body>

<style type="text/css">
    .row {
        margin-block: 15px;
    }
</style>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
<script src="<c:url value="/resources/js/main.js" />" defer></script>

</html>
