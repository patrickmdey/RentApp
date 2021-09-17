<%@ tag language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ attribute name="articleName" required="true" %>
<%@ attribute name="renterFirstName" required="true" %>
<%@ attribute name="renteLastName" required="true" %>
<%@ attribute name="startDate" required="true" %>
<%@ attribute name="endDate" required="true" %>
<%@ attribute name="message" required="true" %>
<%@ attribute name="id" required="true" %>

<div class="card card-style">
    <h3 class="h3 mb-2">${articleName}</h3>
    <p class="lead fw-bold mb-2"><spring:message code="myAccount.ownerRequests.requestFrom"
                                                 arguments="${renterFirstName}, ${renteLastName}"/></p>
    <div class="row">
        <p class="lead col-4"><spring:message code="myAccount.ownerRequests.startDate"
                                              arguments="${startDate}"/></p>
        <p class="lead col-5 ms-n3"><spring:message code="myAccount.ownerRequests.endDate"
                                                    arguments="${endDate}"/></p>
    </div>
    <p class="lead fw-bold"><spring:message code="myAccount.ownerRequests.message"/></p>
    <div class="row">
        <p class="lead col-8">${message}</p>
        <div class="col-4">
            <button class="btn btn-danger"><spring:message code="myAccount.ownerRequests.denyButton"/></button>
            <button class="btn btn-success"><spring:message
                    code="myAccount.ownerRequests.acceptButton"/></button>
        </div>
    </div>
</div>