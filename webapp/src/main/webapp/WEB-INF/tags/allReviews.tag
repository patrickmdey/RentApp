<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="control" tagdir="/WEB-INF/tags/controls" %>

<%@attribute name="article" type="ar.edu.itba.paw.models.Article" required="true" %>
<%@attribute name="user" type="ar.edu.itba.paw.models.User" required="true" %>
<%@attribute name="canReview" type="java.lang.Boolean" required="true" %>
<%@attribute name="maxPage" required="true" %>
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

            <!-- Pagination reviews -->
            <div class="d-flex justify-content-center my-2">
                <nav aria-label="Page navigation">
                    <ul class="pagination justify-content-center">
                        <li class="page-item">
                            <c:url value="${articleUrl}" var="prev" context="/">
                                <c:param name="page" value="${param.page - 1}"/>
                            </c:url>
                            <button class="btn btn-link"
                                    onclick="location.href = '${prev}'" ${(param.page == null || param.page <= 1) ? 'disabled' : '' }>
                                <spring:message code="pagination.previous"/>
                            </button>
                        </li>
                        <c:forEach begin="1" end="${maxPage}" var="page">
                            <li class="page-item">
                                <c:url value="${articleUrl}" var="curr" context="/">
                                    <c:param name="page" value="${page}"/>
                                </c:url>
                                <a class="page-link ${((param.page == null && page == 1) || param.page == page)?'font-weight-bold':''}"
                                   href="${curr}">
                                    <c:out value="${page}"/>
                                </a>
                            </li>
                        </c:forEach>
                        <li class="page-item">
                            <c:url value="${articleUrl}" var="next" context="/">
                                <c:param name="page"
                                         value="${(param.page == null) ? 2 : (param.page + 1)}"/>
                            </c:url>
                            <button class="btn btn-link"
                                    onclick="location.href = '${next}'" ${(param.page >= maxPage || (param.page == null && maxPage == 1))?'disabled':''}>
                                <spring:message code="pagination.next"/>
                            </button>
                        </li>
                    </ul>
                </nav>
            </div>
        </c:otherwise>
    </c:choose>
</div>