<%@ tag language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="controls" tagdir="/WEB-INF/tags/Controls" %>
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
        <%--@elvariable id="accountForm" type="ar.edu.itba.paw.webapp.forms.AccountForm"--%>
        <form:form modelAttribute="accountForm" action="${actionUrl}" method="post" enctype="multipart/form-data">
            <div class="row">
                <div class="col-6">
                    <controls:TextBox path="firstName" type="text"
                                      labelCode="account.form.firstName"
                                      placeholderCode="placeholder.name"
                                      errorCode="errors.requiredName"
                    />
                </div>
                <div class="col-6">
                    <controls:TextBox path="lastName" type="text"
                                      labelCode="account.form.lastName"
                                      placeholderCode="placeholder.lastName"
                                      errorCode="errors.requiredLastName"
                    />
                </div>
            </div>
            <div class="row">
                <c:if test="${!isEdit}">
                    <div class="col-6">
                        <controls:TextBox path="email" type="text"
                                          labelCode="account.form.email"
                                          placeholderCode="placeholder.email"
                                          errorCode="errors.requiredEmail"
                        />
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
                                          placeholderCode="placeholder.password"
                                          errorCode="errors.requiredPassword"/>
                    </div>
                    <div class="col-6">
                        <controls:TextBox path="confirmPassword" type="password"
                                          labelCode="account.form.confirmPassword"
                                          placeholderCode="placeholder.secondPassword"
                                          errorCode="errors.requiredSecondPassword"/>
                    </div>
                </div>
                <h:imageInput path="img"/>
            </c:if>
            <div class="my-2">
                <controls:CheckBox path="isOwner" labelCode="account.form.isOwner"/>
            </div>
            <c:if test="${!isView}">
                <div class="d-flex justify-content-center">
                    <controls:Button col="col-4" color="bg-color-action btn-dark"
                                     labelCode="account.form.publishButton"/>
                </div>
            </c:if>
        </form:form>
    </fieldset>
    <c:if test="${!isView}">
        <div class="d-flex justify-content-center">
            <c:url value="/user/view" var="cancelUrl"/>
            <controls:LinkButton col="col-4" href="${cancelUrl}" color="bg-color-secondary color-rentapp-black"
                                 labelCode="account.form.cancelButton"/>
        </div>
    </c:if>
</div>