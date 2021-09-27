<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@attribute name="articles" type="java.util.List" required="true" %>
<%@attribute name="maxPage" required="true" %>
<c:url value="/" var="currentUrl"/>
<div>
    <div class="row row-cols-3 justify-content-start w-100 container-height">
        <c:forEach var="article" items="${articles}">
            <div class="col">
                <h:marketplaceCard title="${article.title}" price="${article.pricePerDay}"
                                   id="${article.id}"
                                   location="${article.location.name}"
                                   image_id="${article.images.size()==0 ? 1 : article.images.get(0)}"/>
            </div>
        </c:forEach>
    </div>

    <div class="d-flex justify-content-center my-2">
        <nav aria-label="Page navigation">
            <ul class="pagination justify-content-center">
                <li class="page-item">
                    <c:url value="${currentUrl}" var="prev" context="/">
                        <c:param name="page" value="${param.page - 1}"/>
                    </c:url>
                    <button class="btn btn-link"
                            onclick="location.href = '${prev}'" ${(param.page == null || param.page <= 1) ? 'disabled' : '' }>
                        <spring:message code="pagination.previous"/>
                    </button>
                </li>
                <c:forEach begin="1" end="${maxPage}" var="page">
                    <li class="page-item">
                        <c:url value="${currentUrl}" var="curr" context="/">
                            <c:param name="page" value="${page}"/>
                        </c:url>
                        <a class="page-link ${((param.page == null && page == 1) || param.page == page)?'font-weight-bold':''}"
                           href="${curr}">
                            <c:out value="${page}"/>
                        </a>
                    </li>
                </c:forEach>
                <li class="page-item">
                    <c:url value="${currentUrl}" var="next" context="/">
                        <c:param name="page" value="${(param.page == null) ? 2 : (param.page + 1)}"/>
                    </c:url>
                    <button class="btn btn-link"
                            onclick="location.href = '${next}'" ${(param.page >= maxPage || (param.page == null && maxPage == 1))?'disabled':''}>
                        <spring:message code="pagination.next"/>
                    </button>
                </li>
            </ul>
        </nav>
    </div>
</div>