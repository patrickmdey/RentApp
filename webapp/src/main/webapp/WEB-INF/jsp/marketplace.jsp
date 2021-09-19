<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

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
<h:navbar loggedUser="${user}"/>
<div class="container container-height">
    <div class="row filter-container">
        <div class="col-md-3 col-lg-3 col-12"></div>
        <div class="col-md-8 col-lg-8 d-flex justify-content-start">
            <!-- ACA SE MUESTRAN LOS PARAMETROS DE LA URL -->
            <c:if test="${ query != null && query.length() > 0 }">
                Showing results for <c:out value="${query}"/>
            </c:if>

            <c:if test="${ param.location != null && param.location.length() > 0 }">
                <div class="me-2">
                    <i class="bi bi-pin-map"></i>
                    <spring:message code="filters.marketplace.location"/>
                    <c:out value="${locationsEnum[searchForm.location].name}"/>
                </div>
            </c:if>


            <c:if test="${ (param.category != null && param.category.length() > 0)
            || (param.user != null && param.user.length() > 0) }">
                <div class="md-2">
                    <spring:message code="filters.marketplace.filtering"/>
                    <c:if test="${ param.category != null && param.category.length() > 0}">
                        <c:url value="/" var="removeCategoryUrl">
                            <c:forEach var="p" items="${param}">
                                <c:if test="${p.key != 'category'}">
                                    <c:param name="${p.key}" value="${p.value}"/>
                                </c:if>
                            </c:forEach>
                        </c:url>
                        <div class="badge bg-primary mx-2 p-2">
                            <i class="bi bi-tag"></i>
                            <c:out value="${category}"/>
                            <a href="${removeCategoryUrl}" class="text-light">X</a>
                        </div>
                    </c:if>

                    <c:if test="${ param.user != null && param.user.length() > 0}">
                        <c:url value="/" var="removeUserUrl">
                            <c:forEach var="p" items="${param}">
                                <c:if test="${p.key != 'user'}">
                                    <c:param name="${p.key}" value="${p.value}"/>
                                </c:if>
                            </c:forEach>
                        </c:url>
                        <div class="badge bg-primary"><i class="fa fa-user"></i>
                            <c:out value="${userFilter.firstName}"/>
                            <a href="${removeUserUrl}" class="text-light">X</a>
                        </div>
                    </c:if>
                </div>
            </c:if>
        </div>
    </div>
    <div class="row">
        <div class="card card-style filters-card col-md-3 col-lg-3 col-12">
            <form:form modelAttribute="searchForm" action="${marketplaceUrl}" method="get">
                <h4 class="h4 color-rentapp-black"><spring:message code="filter.title"/></h4>
                <hr/>
                <div class="d-flex justify-content-start">
                    <div>
                        <form:label path="query"/>
                        <spring:message code="filterForm.search" var="searchPlaceholder"/>
                        <form:input type="text" path="query" placeholder="${searchPlaceholder}"/>
                    </div>
                </div>

                <form:input type="number" path="user" cssClass="d-none"/>

                <div class="form-check d-flex flex-column align-items-start">
                    <form:label path="category"><spring:message
                            code="search.rentRequestForm.category"/></form:label>
                    <form:radiobuttons path="category" items="${categories}"
                                       itemValue="id" itemLabel="description"/>
                </div>

                <div>
                    <form:label path="orderBy">
                        <spring:message code="filterForm.orderBy"/>
                    </form:label>
                    <div class="my-2 row align-items-center">
                        <div class="col-10">
                            <form:select path="orderBy" class="form-control form-control-custom">
                                <c:forEach var="option" items="${orderOptions}">
                                    <form:option value="${option.column}">
                                        <spring:message code="${option.description}"/>
                                    </form:option>
                                </c:forEach>
                            </form:select>
                        </div>
                        <i class="col-2 ms-n2 color-rentapp-black fa-lg bi bi-filter-right"></i>
                    </div>
                </div>

                <div>
                    <form:label path="location">
                        <spring:message code="filterForm.location"/>
                    </form:label>
                    <div class="my-1 row align-items-center">
                        <div class="col-10">
                            <form:select path="location" class="form-control form-control-custom">
                                <form:option value=""
                                             label="Todas las ubicaciones">Todas las ubicaciones</form:option>
                                <c:forEach var="loc" items="${locations}">
                                    <form:option value="${loc.ordinal()}" label="${loc.name}">
                                        <c:out value="${loc.name}"/>
                                    </form:option>
                                </c:forEach>
                            </form:select>

                        </div>
                        <i class="col-2 ms-n2 color-rentapp-black fa-lg bi bi-pin-map"></i>
                    </div>
                </div>

                <button type="submit" class="btn bg-color-action btn-dark my-auto">
                    <spring:message code="filterForm.search"/>
                </button>
            </form:form>
        </div>

        <div class="col-md-9 col-lg-9 col-12">
            <c:choose>
                <c:when test="${articles.size() == 0}">
                    <div class="card card-style align-items-center justify-content-center">
                        <h2 class="h2">
                            <spring:message code="marketplace.articleNotFound.title"/>
                        </h2>
                        <p class="lead">
                            <spring:message code="marketplace.articleNotFound.subtitle"/>
                        </p>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="row row-cols-3 justify-content-center">
                        <c:forEach var="article" items="${articles}">
                            <div class="col d-flex justify-content-center">
                                <h:marketplaceCard title="${article.title}" price="${article.pricePerDay}"
                                                   id="${article.id}"
                                                   location="${article.location}"
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
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
<h:footer/>
</body>
<h:mainScript/>
</html>
