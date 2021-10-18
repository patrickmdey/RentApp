<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<h:head title="title.myRequests"/>
<body class="bg-color-grey">
<h:navbar loggedUser="${user}"/>

<c:choose>
    <c:when test="${state.isPending}">
        <c:url value="/user/my-requests/pending" var="currentUrl"/>
        <c:set var="stateColor" value="${1}"/>
    </c:when>
    <c:when test="${state.isAccepted}">
        <c:url value="/user/my-requests/accepted" var="currentUrl"/>
        <c:set var="stateColor" value="${2}"/>
    </c:when>
    <c:otherwise>
        <c:url value="/user/my-requests/declined" var="currentUrl"/>
        <c:set var="stateColor" value="${3}"/>
    </c:otherwise>
</c:choose>


<div class="container min-height">
    <div class="row align-items-start justify-content-center mb-2">
        <div class="col-md-3 col-lg-3 col-12"></div>
        <div class="col-md-9 col-lg-9 col-12">
            <div class="row cols-3 g-1 justify-content-between">
                <div class="col">
                    <a class="btn w-100 ${stateColor == 1? "bg-color-secondary btn-dark":"bg-color-action color-grey"}"
                       href="<c:url value="/user/my-requests/pending"/>">
                        <spring:message code="myAccount.ownerRequests.myRequests.pending"/>
                    </a>
                </div>
                <div class="col">
                    <a class="btn w-100 ${stateColor == 2? "bg-color-secondary btn-dark":"bg-color-action color-grey"}"
                       href="<c:url value="/user/my-requests/accepted"/>">
                        <spring:message code="myAccount.ownerRequests.myRequests.accepted"/>
                    </a>
                </div>
                <div class="col">
                    <a class="btn w-100 ${stateColor == 3? "bg-color-secondary btn-dark":"bg-color-action color-grey"}"
                       href="<c:url value="/user/my-requests/declined"/>">
                        <spring:message code="myAccount.ownerRequests.myRequests.declined"/>
                    </a>
                </div>
            </div>
        </div>
    </div>


    <div class="row align-items-start justify-content-center">
        <div class="card card-style filters-card col-md-3 col-lg-3 col-12">
            <h4 class="h4"><spring:message code="title.myRequests"/></h4>
            <hr/>
            <div>
                <nav class="nav nav-pills" id="nav-tab" role="tablist">
                    <c:if test="${user.type.isOwner}">
                        <a class="nav-link active w-100 text-start bg-color-action" id="nav-received-tab"
                           data-bs-toggle="pill"
                           href="#nav-received"
                           role="tab"
                           aria-controls="nav-owned" aria-selected="true">
                            <p class="my-1 color-grey">
                                <spring:message code="requests.received"/>
                            </p>
                        </a>
                    </c:if>
                    <a class="nav-link bg-color-action ${!user.type.isOwner?' active':''} text-start w-100"
                       id="nav-sent-tab"
                       data-bs-toggle="pill"
                       href="#nav-sent" role="tab"
                       aria-controls="nav-rented" aria-selected="${user.type.isOwner?'false':'true'}">
                        <p class="my-1 color-grey">
                            <spring:message code="requests.sent"/>
                        </p>
                    </a>
                </nav>
            </div>
        </div>
        <div class="col-md-9 col-lg-9 col-12">
            <div class="tab-content" id="nav-tabContent">
                <div class="tab-pane fade ${user.type.isOwner?' show active':''}" id="nav-received"
                     role="tabpanel"
                     aria-labelledby="nav-received-tab">
                    <h:allRequests proposals="${receivedProposals}" userId="${user.id}" state="${state.name()}"
                                   isReceived="${true}" currentUrl="${currentUrl}" maxPage="${sentMaxPage}"/>
                </div>
                <div class="tab-pane fade ${!user.type.isOwner?' show active':''}" id="nav-sent" role="tabpanel"
                     aria-labelledby="nav-sent-tab">
                    <h:allRequests proposals="${sentProposals}" userId="${user.id}" state="${state.name()}"
                                   isReceived="${false}" currentUrl="${currentUrl}" maxPage="${sentMaxPage}"/>
                </div>
            </div>
        </div>
    </div>
</div>
<h:footer/>
</body>
<h:mainScript/>
</html>
