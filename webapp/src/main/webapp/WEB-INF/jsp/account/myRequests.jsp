<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<h:head title="Manage Account"/>
<body class="bg-color-grey">
<h:navbar loggedUser="${user}"/>
<div class="row">
    <div class="card card-style col-3">
        <button class="btn btn-link w-100"><spring:message
                code="myAccount.ownerRequests.myRequests"/></button>
        <button class="btn btn-link w-100"><spring:message
                code="myAccount.ownerRequests.activeRequests"/></button>
        <button class="btn btn-link w-100"><spring:message
                code="myAccount.ownerRequests.endedRequests"/></button>
    </div>
    <div class="col-8">
        <h1 class="h1"><spring:message code="myAccount.ownerRequests.myRequestsTitle"/></h1>
        <hr/>
        <c:forEach var="request" items="${requests}">

            <h:requestCard articleName="${request.article.title}" renterFirstName="${request.renter.firstName}"
                           renteLastName="${request.renter.lastName}"
                           startDate="${request.startDate}" endDate="${request.endDate}"
                           message="${request.message}" id="${request.id}" state="${request.approved}"
                           userId="${user.id}"/>
        </c:forEach>
    </div>
</div>
<h:footer/>
</body>
</html>