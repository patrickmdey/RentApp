<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="control" tagdir="/WEB-INF/tags/controls" %>

<%@attribute name="article" type="ar.edu.itba.paw.models.Article" required="true" %>
<%@attribute name="user" type="ar.edu.itba.paw.models.User" required="true" %>
<%@attribute name="canReview" type="java.lang.Boolean" required="true" %>
<%@attribute name="maxPage" required="true" type="java.lang.Integer" %>
<%@attribute name="reviews" type="java.util.List" required="true" %>

<c:url value="/article/${article.id}" var="articleUrl"/>
<c:url value="/review/${article.id}/create" var="writeReview"/>

<div>
    <div class="row">
        <h3 class="col-8 h3"><spring:message code="account.reviews.title"/></h3>
        <c:if test="${canReview}">
            <div class="col-4">
                <control:LinkButton href="${writeReview}" labelCode="article.createReview.title"
                                    color="bg-color-action color-grey"/>
            </div>
        </c:if>
    </div>
    <hr/>
    <c:choose>
        <c:when test="${reviews.size() == 0}">
            <p class="lead"><spring:message code="article.noReviews"/></p>
        </c:when>
        <c:otherwise>
            <c:forEach items="${reviews}" var="review">
                <h:review articleId="${article.id}" review="${review}" userId="${user.id}"/>
            </c:forEach>

            <h:pagination currentUrl="${articleUrl}" maxPage="${maxPage}"/>
        </c:otherwise>
    </c:choose>
</div>