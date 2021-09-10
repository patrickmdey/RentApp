<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:url value="/" var="marketplaceUrl"/>
<html>
<h:head/>
<body class="article-background">
<h:navbar/>
<div class="container">
    <div class="row">
        <div class="col-md-3 col-lg-3">


<%--            <div class="card bg-light">--%>
<%--                <c:forEach var="category" items="${categories}">--%>
<%--                    <div class="form-check">--%>
<%--                        <input class="form-check-input" type="radio" name="radioDefault" id="formRadioChecked"--%>
<%--                               checked="">--%>
<%--                        <label class="form-check-label" for="formRadioChecked">${category.description}</label>--%>
<%--                    </div>--%>
<%--                </c:forEach>--%>
<%--            </div>--%>

            <div class="card bg-light">
                <form:form modelAttribute="searchForm" action="${marketplaceUrl}" method="get">
                    <div class="form-input">
                        <form:label path="category"><spring:message code="article.form.name"/></form:label>
                        <form:select path="category" class="form-control form-control-custom">
                            <option value="">Select</option>
                            <c:forEach var="category" items="${categories}">
                                <option value="${category.id}">${category.description}</option>
                            </c:forEach>
                        </form:select>
                    </div>
                    <div class="form-input">
                        <form:label path="orderBy"><spring:message code="article.form.name"/></form:label>
                        <form:select path="orderBy" class="form-control form-control-custom">
                            <c:forEach var="option" items="${orderOptions}">
                                <option value="${option.column}"><spring:message code="${option.description}"/></option>
                            </c:forEach>
                        </form:select>
                        <form:errors path="orderBy" element="p" cssClass="text-danger"/>
                    </div>
                    <button type="submit">Search</button>
                </form:form>
            </div>
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
                                       location="${article.location}"/>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
</body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
<script src="<c:url value="/resources/js/main.js" />" defer></script>
</html>
