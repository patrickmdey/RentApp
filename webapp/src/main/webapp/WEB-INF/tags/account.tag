<%@ tag language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="controls" tagdir="/WEB-INF/tags/controls" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>

<%@ attribute name="mode" required="true" %>
<%@ attribute name="locations" type="java.util.List" required="true" %>

<c:set var="isView" value="${mode == 'view'}"/>
<c:set var="isEdit" value="${mode == 'edit'}"/>
<c:set var="isCreate" value="${mode == 'create'}"/>

<c:if test="${isCreate}">
    <c:url value="/user/register" var="actionUrl"/>
</c:if>
<c:if test="${isEdit}">
    <c:url value="/user/edit" var="actionUrl"/>
</c:if>

<div>
    <fieldset ${isView ? "disabled" : ""}>
        <form:form modelAttribute="accountForm" action="${actionUrl}" method="post" enctype="multipart/form-data">
            <div class="row">
                <div class="col-6">
                    <controls:TextBox path="firstName" type="text"
                                      labelCode="account.form.firstName"
                                      placeholderCode="placeholder.name"/>

                </div>
                <div class="col-6">
                    <controls:TextBox path="lastName" type="text"
                                      labelCode="account.form.lastName"
                                      placeholderCode="placeholder.lastName"/>

                </div>
            </div>
            <div class="row">
                <c:if test="${!isEdit}">
                    <div class="col-6">
                        <controls:TextBox path="email" type="text"
                                          labelCode="account.form.email"
                                          placeholderCode="placeholder.email"/>

                    </div>
                </c:if>
                <div class="col-6">

                    <controls:LocationSelect items="${locations}" path="location"
                                             labelCode="account.form.location"
                                             errorCode="errors.requiredLocation"
                    />
                </div>

            </div>
            <c:if test="${isCreate}">
                <div class="row">
                    <div class="col-6">
                        <controls:TextBox path="password" type="password"
                                          labelCode="account.form.password"
                                          placeholderCode="placeholder.password"/>
                    </div>
                    <div class="col-6">
                        <controls:TextBox path="confirmPassword" type="password"
                                          labelCode="account.form.confirmPassword"
                                          placeholderCode="placeholder.secondPassword"/>
                    </div>
                </div>
                <h:imageInput path="img"/>
            </c:if>
            <c:if test="${!isEdit}">
                <div class="my-2">
                    <controls:CheckBox path="isOwner" labelCode="account.form.isOwner"/>
                </div>
            </c:if>
            <c:if test="${isCreate}">
                <div class="d-flex justify-content-center mt-2">
                    <controls:Button col="col-4" color="bg-color-action btn-dark"
                                     labelCode="account.form.publishButton"/>
                </div>
            </c:if>
            <c:if test="${isEdit}">

                <c:url value="/user/view" var="cancelUrl"/>
                <div class="d-flex justify-content-end">
                    <button type="submit" class="rounded btn bg-color-action color-grey me-1">
                        <spring:message code="account.form.publishButton"/>
                    </button>
                    <a href="${cancelUrl}" class="rounded btn btn-link color-danger">
                        <spring:message code="account.form.cancelButton"/>
                    </a>
                </div>
            </c:if>
        </form:form>
    </fieldset>
</div>