<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>

<%@ attribute name="proposals" type="java.util.List" required="true" %>
<%@ attribute name="userId" required="true" %>
<%@ attribute name="state" required="true" %>
<%@ attribute name="isReceived" type="java.lang.Boolean" required="true" %>
<%@ attribute name="currentUrl" required="true" %>
<%@attribute name="maxPage" required="true" %>

<c:choose>
    <c:when test="${proposals.size() != 0}">
        <c:forEach var="request" items="${proposals}">
            <h:requestCard articleName="${request.article.title}"
                           renterFirstName="${request.renter.firstName}"
                           renteLastName="${request.renter.lastName}"
                           startDate="${request.startDate}" endDate="${request.endDate}"
                           message="${request.message}" id="${request.id}" state="${request.state}"
                           userId="${userId}" renterEmail="${request.renter.email}"
                           isReceived="${isReceived}" articleId="${request.article.id}"
            />
        </c:forEach>
        <h:pagination currentUrl="${currentUrl}" maxPage="${maxPage}"/>
    </c:when>
    <c:otherwise>
        <div class="card card-style align-items-center pa-3 justify-content-center">
            <c:choose>
                <c:when test="${isReceived}">
                    <h2 class="h2">
                        <spring:message code="myAccount.ownerRequest.noRequestsFound.${state}.received"/>
                    </h2>
                    <p class="lead">
                        <spring:message code="myAccount.ownerRequest.noRequestsFound.${state}.subtitle.received"/>
                    </p>
                </c:when>
                <c:otherwise>
                    <h2 class="h2">
                        <spring:message code="myAccount.ownerRequest.noRequestsFound.${state}.sent"/>
                    </h2>
                    <p class="lead">
                        <spring:message code="myAccount.ownerRequest.noRequestsFound.${state}.subtitle.sent"/>
                    </p>
                </c:otherwise>
            </c:choose>
        </div>
    </c:otherwise>
</c:choose>