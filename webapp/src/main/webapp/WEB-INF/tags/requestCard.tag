<%@ tag language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ attribute name="articleName" required="true" %>
<%@ attribute name="firstName" required="true" %>
<%@ attribute name="lastName" required="true" %>
<%@ attribute name="startDate" required="true" type="java.time.LocalDate" %>
<%@ attribute name="endDate" required="true" type="java.time.LocalDate" %>
<%@ attribute name="message" required="true" %>
<%@ attribute name="id" required="true" %>
<%@ attribute name="articleId" required="true" %>
<%@ attribute name="state" required="true" %>
<%@ attribute name="userId" required="true" %>
<%@ attribute name="email" required="true" %>
<%@ attribute name="imageId" required="true" %>
<%@ attribute name="isReceived" type="java.lang.Boolean" required="true" %>
<%@ attribute name="marked" required="true" %>


<c:url value="/user/my-requests/${id}/accept" var="acceptRequest"/>
<c:url value="/user/my-requests/${id}/delete" var="deleteRequest"/>
<c:url value="/article/${articleId}" var="goToArticle"/>

<!-- Modal -->
<div class="modal fade" id="rejectRequestModal" tabindex="-1" aria-labelledby="rejectRequestModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="rejectRequestModalLabel">
                    <spring:message code="myAccount.ownerRequests.rejectTitle"/>
                </h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <spring:message code="myAccount.ownerRequests.rejectWarningMessage"/>
            </div>
            <div class="modal-footer">
                <form method="post" action="${deleteRequest}">
                    <button type="submit" class="btn btn-danger">
                        <spring:message code="myAccount.ownerRequests.rejectButton"/>
                    </button>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="card card-style my-requests-card my-2">
    <a href="${goToArticle}" class="d-flex w-100 justify-content-start align-items-center mb-2">
        <c:if test="${marked}">
            <i class="bi bi-circle-fill color-rentapp-red me-3"></i>
        </c:if>
        <h3 class="h3 color-action mb-0"><c:out value="${articleName}"/></h3>
    </a>
    <hr>
    <div class="row">
        <div class="col-3">
            <img src="<c:url value="/image/${imageId}"/>" class="card-image"
                 alt="<c:out value="${articleName}"/> - image">
        </div>
        <div class="col-9">
            <p class="lead mb-2"><spring:message code="myAccount.ownerRequests.requestContact"
                                                 arguments="${firstName}, ${lastName}"/></p>
            <div class="row justify-content-start">
                <p class="lead col-lg-6 col-md-6 col-12"><spring:message code="myAccount.ownerRequests.startDate"
                                                                         arguments="${startDate}"/></p>
                <p class="lead col-lg-6 col-md-6 col-12"><spring:message code="myAccount.ownerRequests.endDate"
                                                                         arguments="${endDate}"/></p>
            </div>
            <c:if test="${state == 1}">
                <div class="d-flex">
                    <p class="lead"><spring:message code="footer.contact"/></p>
                    <a href="mailto:${email}" class="lead ms-1">
                        <c:out value="${email}"/>
                    </a>
                </div>
            </c:if>
            <h4 class="h4">
                <spring:message code="myAccount.ownerRequests.message"/>
            </h4>
            <p><c:out value="${message}"/></p>
        </div>
    </div>
    <c:if test="${isReceived && state == 0}">
        <div class="d-flex justify-content-end align-items-center">
            <form:form method="post" action="${acceptRequest}">
                <button type="submit" class="btn bg-color-action color-grey me-1">
                    <spring:message code="myAccount.ownerRequests.acceptButton"/>
                </button>

                <button type="button" class="btn btn-link color-danger" data-bs-toggle="modal"
                        data-bs-target="#rejectRequestModal">
                    <spring:message code="myAccount.ownerRequests.rejectButton"/>
                </button>
            </form:form>
        </div>
    </c:if>
</div>