<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>

<%@ attribute name="proposals" type="java.util.List" required="true" %>
<%@ attribute name="userId" required="true" %>
<%@ attribute name="state" required="true" %>

<c:choose>
    <c:when test="${proposals.size() != 0}">
        <c:forEach var="request" items="${proposals}">

            <h:requestCard articleName="${request.article.title}"
                           renterFirstName="${request.renter.firstName}"
                           renteLastName="${request.renter.lastName}"
                           startDate="${request.startDate}" endDate="${request.endDate}"
                           message="${request.message}" id="${request.id}" state="${request.state}"
                           userId="${userId}" renterEmail="${request.renter.email}"/>
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