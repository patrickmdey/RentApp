<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<h:head title="Manage Account"/>
<body class="bg-color-grey">
<h:navbar loggedUser="${user}"/>

<c:choose>
    <c:when test="${state == 'ACCEPTED'}">
        <c:url value="/user/my-requests/accepted" var="currentUrl" />
    </c:when>
    <c:when test="${state == 'REJECTED'}">
        <c:url value="/user/my-requests/declined" var="currentUrl" />
    </c:when>
    <c:otherwise>
        <c:url value="/user/my-requests/pending" var="currentUrl" />
    </c:otherwise>
</c:choose>

<div class="main-container">
    <h1 class="h1"><spring:message code="myAccount.ownerRequests.myRequestsTitle"/></h1>
    <hr/>
    <div class="row w-100 g-0 align-items-start justify-content-between">
        <div class="card card-style col-3">
            <a class="btn btn-light mb-3" href="<c:url value="/user/my-requests/pending"/>">
                <spring:message code="myAccount.ownerRequests.myRequests.pending"/>
            </a>
            <a class="btn btn-light mb-3" href="<c:url value="/user/my-requests/accepted"/>">
                <spring:message code="myAccount.ownerRequests.myRequests.accepted"/>
            </a>
            <a class="btn btn-light" href="<c:url value="/user/my-requests/declined"/>">
                <spring:message code="myAccount.ownerRequests.myRequests.declined"/>
            </a>
        </div>
        <div class="col-8">
            <div class="card w-100 p-5">
                <nav class="nav nav-tabs mb-2" id="nav-tab" role="tablist">
                    <a class="nav-link active" id="nav-received-tab" data-bs-toggle="tab" href="#nav-received" role="tab"
                       aria-controls="nav-owned" aria-selected="true">
                        <p class="lead my-1">
                            <spring:message code="requests.received"/>
                        </p>
                    </a>

                    <a class="nav-link" id="nav-sent-tab" data-bs-toggle="tab" href="#nav-sent" role="tab"
                       aria-controls="nav-rented" aria-selected="false">
                        <p class="lead my-1">
                            <spring:message code="requests.sent"/>
                        </p>
                    </a>
                </nav>
                <div class="tab-content" id="nav-tabContent">
                    <div class="tab-pane fade show active p-3" id="nav-received" role="tabpanel" aria-labelledby="nav-received-tab">
                        <h:allRequests proposals="${receivedProposals}" userId="${user.id}" state="${state}"/>
                        <h:pagination currentUrl="${currentUrl}" maxPage="${receivedMaxPage}"/>
                    </div>
                    <div class="tab-pane fade" id="nav-sent" role="tabpanel" aria-labelledby="nav-sent-tab">
                        <h:allRequests proposals="${sentProposals}" userId="${user.id}" state="${state}"/>
                        <h:pagination currentUrl="${currentUrl}" maxPage="${sentMaxPage}"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<h:footer/>
</body>
<h:mainScript/>
</html>
