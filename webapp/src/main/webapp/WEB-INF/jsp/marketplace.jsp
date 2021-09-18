<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sping" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:url value="/" var="marketplaceUrl"/>
<c:url value="/" var="currentUrl">
    <c:forEach items="${param}" var="entry">
        <c:if test="${entry.key != 'page'}">
            <c:param name="${entry.key}" value="${entry.value}"/>
        </c:if>
    </c:forEach>
</c:url>
<html>
<h:head title="RentApp"/>
<body class="bg-color-grey">
<h:navbar/>
<div class="container container-height">
    <div class="row">
        <div class="col-md-3 col-lg-3">
            <form:form modelAttribute="searchForm" action="${marketplaceUrl}" method="get">
                <div class="form-input">
                    <form:label path="query"/>
                    <spring:message code="filterForm.search" var="searchPlaceholder"/>
                    <form:input type="text" path="query" placeholder="${searchPlaceholder}"/>
                </div>

                <form:input type="number" path="user" cssClass="d-none"/>

                <div class="form-check card bg-light">
                    <form:label path="category"><spring:message code="search.rentRequestForm.category"/></form:label>
                    <form:radiobuttons path="category" items="${categories}"
                                       itemValue="id" itemLabel="description"/>
                </div>
                <div class="form-input">
                    <form:label path="orderBy"><spring:message code="filterForm.orderBy"/></form:label>
                    <form:select path="orderBy" class="form-control form-control-custom">
                        <c:forEach var="option" items="${orderOptions}">
                            <form:option value="${option.column}">
                                <spring:message code="${option.description}"/>
                            </form:option>
                        </c:forEach>
                    </form:select>
                </div>

                <div class="form-input">
                    <form:label path="location">
                        <spring:message code="filterForm.location"/>
                    </form:label>
                    <form:select path="location" class="form-control form-control-custom">
                        <form:option value="" label="Todas las ubicaciones">Todas las ubicaciones</form:option>
                        <c:forEach var="loc" items="${locations}">
                            <form:option value="${loc.ordinal()}" label="${loc.name}">
                                <c:out value="${loc.name}"/>
                            </form:option>
                        </c:forEach>
                    </form:select>
                </div>


                <button type="submit">Search</button>
            </form:form>
        </div>

        <div class="col-md-8 col-lg-8 col-12 ms-md-5 ms-lg-5">

            <!-- ACA SE MUESTRAN LOS PARAMETROS DE LA URL -->
            <c:if test="${ query != null && query.length() > 0 }">
                Showing results for <c:out value="${query}"/>
            </c:if>


            <c:if test="${ param.category != null }">
                Searching category
                <c:url value="/" var="removeCategoryUrl">
                    <c:forEach var="p" items="${param}">
                        <c:if test="${p.key != 'category'}">
                            <c:param name="${p.key}" value="${p.value}"/>
                        </c:if>
                    </c:forEach>
                </c:url>
                <div class="badge bg-primary">
                    <c:out value="${category}"/>
                    <a href="${removeCategoryUrl}" class="text-light">X</a>
                </div>
            </c:if>

            <c:if test="${ param.location != null && param.location.length() > 0 }">
                Searching location
                <c:out value="${locationsEnum[searchForm.location].name}"/>
            </c:if>

            <c:if test="${ param.user != null && param.user.length() > 0}">
                Searching user
                <c:url value="/" var="removeUserUrl">
                    <c:forEach var="p" items="${param}">
                        <c:if test="${p.key != 'user'}">
                            <c:param name="${p.key}" value="${p.value}"/>
                        </c:if>
                    </c:forEach>
                </c:url>
                <div class="badge bg-primary">
                    <c:out value="${userFilter.firstName}"/>
                    <a href="${removeUserUrl}" class="text-light">X</a>
                </div>
            </c:if>


            <c:if test="${articles.size() == 0}">
                No results found
            </c:if>
            <div class="row row-cols-3">
                <c:forEach var="article" items="${articles}">
                    <div class="col d-flex justify-content-center">
                        <h:articleCard title="${article.title}" price="${article.pricePerDay}" id="${article.id}"
                                       location="${article.location}"
                                       image_id="${article.images.size()==0 ? 1 : article.images.get(0)}"/>
                    </div>
                </c:forEach>
            </div>

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

                    <%--                    <c:if test="${param.page} > 1">--%>
                    <%--                        <li class="page-item">--%>
                    <%--                            <c:url value="${marketplaceUrl}" var="paginationUrlMinus1">--%>
                    <%--                                <c:param name="page" value="${param.page - 1}"/>--%>
                    <%--                            </c:url>--%>
                    <%--                            <a class="page-link" href="${paginationUrlMinus1}" aria-label="Previous">--%>
                    <%--                                <span class="color-rentapp-black" aria-hidden="true"><spring:message--%>
                    <%--                                        code="pagination.previous"/></span>--%>
                    <%--                            </a>--%>
                    <%--                        </li>--%>
                    <%--                    </c:if>--%>
                    <%--                    <c:url value="${marketplaceUrl}" var="paginationUrlPlus1">--%>
                    <%--                        <c:param name="page" value="${param.page + 1}"/>--%>
                    <%--                    </c:url>--%>
                    <%--                    <li class="page-item"><a href="#"></a></li>--%>
                    <%--                    <c:url value="${marketplaceUrl}" var="paginationUrlPlus2">--%>
                    <%--                        <c:param name="page" value="${param.page + 2}"/>--%>
                    <%--                    </c:url>--%>
                    <%--                    <li class="page-item"><a class="page-link" href="${paginationUrlPlus2}"></a>${param.page + 2}</li>--%>
                    <%--                    <li><p class="lead">...</p></li>--%>
                    <%--                    <c:url value="${marketplaceUrl}" var="paginationUrlLast">--%>
                    <%--                        <c:param name="page" value="${maxPage}"/>--%>
                    <%--                    </c:url>--%>
                    <%--                    <li class="page-item"><a class="page-link" href="${paginationUrlLast}"></a>${maxPage}</li>--%>
                    <%--                    <c:if test="${param.page} < ${maxPage}">--%>
                    <%--                        <li class="page-item">--%>
                    <%--                            <a class="page-link color-rentapp-black" href="${paginationUrlPlus1}" aria-label="Next">--%>
                    <%--                                <span aria-hidden="true"><spring:message code="pagination.next"/></span>--%>
                    <%--                            </a>--%>
                    <%--                        </li>--%>
                    <%--                    </c:if>--%>
                </ul>
            </nav>

        </div>
    </div>
</div>
<h:footer/>
</body>
<h:mainScript/>
</html>
