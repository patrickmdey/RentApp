<%@ tag language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="controls" tagdir="/WEB-INF/tags/Controls" %>

<%@ attribute name="mode" required="true" %>

<c:set var="isView" value="${mode == \"view\"}"/>
<c:set var="isEdit" value="${mode == \"edit\"}"/>
<c:set var="isCreate" value="${mode == \"create\"}"/>

<c:if test="${isCreate}">
    <c:url value="/user/register" var="actionUrl"/>
</c:if>
<c:if test="${isEdit}">
    <c:url value="/user/edit" var="actionUrl"/>
</c:if>

<html>
<body>
<fieldset ${isView ? "disabled" : ""}>
    <%--@elvariable id="accountForm" type="ar.edu.itba.paw.webapp.forms.AccountForm"--%>
    <form:form modelAttribute="accountForm" action="${actionUrl}" method="post">
        <div class="row">
            <div class="col ">
                <controls:TextBox path="firstName" type="text" labelCode="account.form.firstName"/>
            </div>
            <div class="col ">
                <controls:TextBox path="lastName" type="text" labelCode="account.form.lastName"/>
            </div>
        </div>
        <div class="row">
            <div class="col ">
                <controls:TextBox path="email" type="text" labelCode="account.form.email"/>
            </div>
            <div class="col">
                <controls:TextBox path="location" type="text" labelCode="account.form.location"/>
            </div>

        </div>
        <c:if test="${isCreate}">
            <div class="row">
                <div class="col ">
                    <controls:TextBox path="password" type="password" labelCode="account.form.password"/>
                </div>
                <div class="col ">
                    <controls:TextBox path="confirmPassword" type="password" labelCode="account.form.confirmPassword"/>
                </div>
            </div>
        </c:if>
        <div class="row">
            <controls:CheckBox path="isOwner" labelCode="account.form.isOwner"/>
        </div>
        <c:if test="${!isView}">
            <div class="row justify-content-space-around">

                <controls:Button col="col-10" color="btn-primary" labelCode="account.form.publishButton"/>

            </div>
        </c:if>
    </form:form>
</fieldset>
<div class="row justify-content-space-around">
    <controls:LinkButton href="/marketplace" col="col-10" color="btn-secondary" labelCode="account.form.cancelButton"/>
</div>
</body>

<style type="text/css">
    .justify-content-space-around {
        justify-content: space-around;
    }

    .row {
        margin-block: 15px;
    }

    .justify-content-center {
        justify-content: center;
    }
</style>
</html>