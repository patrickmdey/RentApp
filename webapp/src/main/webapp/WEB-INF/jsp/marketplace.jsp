<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:url value="/" var="marketplaceUrl"/>
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
                    <form:input type="text" path="query" placeholder="Search"/>
                </div>

                <form:input type="number" path="user" cssClass="d-none"/>

                <div class="form-check card bg-light">
                    <form:label path="category"><spring:message code="search.rentRequestForm.category"/></form:label>
                    <form:radiobuttons path="category" items="${categories}"
                                       itemValue="id" itemLabel="description"/>
                </div>
                <div class="form-input">
                    <form:label path="orderBy"><spring:message code="article.rentRequestForm.name"/></form:label>
                    <form:select path="orderBy" class="form-control form-control-custom">
                        <c:forEach var="option" items="${orderOptions}">
                            <option value="${option.column}" ${option.column == searchForm.orderBy? "selected":""}>
                                <spring:message code="${option.description}"/>
                            </option>
                        </c:forEach>
                    </form:select>
                </div>
                <button type="submit">Search</button>
            </form:form>
        </div>
        <div class="col-md-8 col-lg-8 col-12 ms-md-5 ms-lg-5">
            <c:if test="${query != null && query.length() > 0 }">
                Showing results for <c:out value="${query}"/>
            </c:if>
            <c:if test="${articles.size() == 0}">
                No results found
            </c:if>
            <div class="row row-cols-3">
                <c:forEach var="article" items="${articles}">
                    <div class="col d-flex justify-content-center">
                        <h:articleCard title="${article.title}" price="${article.pricePerDay}" id="${article.id}"
                                       location="${article.location}" image_id="${article.images.size()==0 ? 1 : article.images.get(0)}"/>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
<h:footer/>
</body>
<h:mainScript/>
</html>
