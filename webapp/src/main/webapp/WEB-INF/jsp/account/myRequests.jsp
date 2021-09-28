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
            <c:choose>
                <c:when test="${requests.size() != 0}">
                    <c:forEach var="request" items="${requests}">

                        <h:requestCard articleName="${request.article.title}"
                                       renterFirstName="${request.renter.firstName}"
                                       renteLastName="${request.renter.lastName}"
                                       startDate="${request.startDate}" endDate="${request.endDate}"
                                       message="${request.message}" id="${request.id}" state="${request.state}"
                                       userId="${user.id}" renterEmail="${request.renter.email}"/>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div class="card card-style align-items-center justify-content-center">
                        <h2 class="h2">
                            <spring:message code="myAccount.ownerRequest.noRequestsFound.${state}"/>
                        </h2>
                        <p class="lead">
                            <spring:message code="myAccount.ownerRequest.noRequestsFound.${state}.subtitle"/>
                        </p>
                    </div>
                </c:otherwise>
            </c:choose>
            <h:pagination currentUrl="${currentUrl}" maxPage="${maxPage}"/>
        </div>
    </div>
</div>
<h:footer/>
</body>
<h:mainScript/>
</html>
