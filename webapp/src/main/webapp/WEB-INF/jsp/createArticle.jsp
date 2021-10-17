<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<c:choose>
    <c:when test="${articleId == null}">
        <c:url value="/article/create" var="articleUrl"/>
        <c:url value="/" var="goBack"/>
        <c:set var="titleCode" value="article.createArticleForm.title"/>
        <c:set var="confirmButton" value="article.createArticleForm.publish"/>
    </c:when>
    <c:otherwise>
        <c:url value="/article/${articleId}/edit" var="articleUrl"/>
        <c:url value="/article/${articleId}" var="goBack"/>
        <c:set var="titleCode" value="article.editArticleForm.title"/>
        <c:set var="confirmButton" value="article.editArticleForm.confirm"/>
    </c:otherwise>
</c:choose>
<html>
<h:head title="title.createArticle"/>
<body class="bg-color-grey">
<h:navbar loggedUser="${user}"/>
<div class="main-container min-height">
    <div class="card shadow card-style create-card mx-3">
        <form:form modelAttribute="createArticleForm" action="${articleUrl}" method="post"
                   enctype="multipart/form-data">
            <div class="form-container">
                <h3 class="h3 fw-bold my-1"><spring:message code="${titleCode}"/></h3>

                <hr/>
                <div class="my-2">
                    <form:label path="name"><spring:message code="article.createArticleForm.name"/></form:label>
                    <spring:message code="placeholder.articleName" var="articleName"/>
                    <form:input type="text" path="name" class="form-control form-control-custom"
                                placeholder="${articleName}"/>
                    <form:errors path="name" element="p" cssClass="error">
                    </form:errors>
                </div>
                <div class="col-12 my-2">
                    <form:label path="description"><spring:message
                            code="article.createArticleForm.description"/></form:label>
                    <spring:message code="placeholder.articleDescription" var="articleDescription"/>
                    <form:textarea type="text" path="description" class="form-control form-control-custom"
                                   placeholder="${articleDescription}"/>
                    <form:errors path="description" element="p" cssClass="error"/>
                </div>
                <div class="col-12 my-2">
                    <form:label path="pricePerDay"><spring:message code="article.createArticleForm.price"/></form:label>
                    <div class="input-group">
                        <span class="input-group-text"><spring:message code="currency.symbol"/></span>
                        <form:input type="number" step="0.01" placeholder="0.0" path="pricePerDay"
                                    class="form-control form-control-custom"/>
                    </div>
                    <form:errors path="pricePerDay" element="p" cssClass="error"/>
                </div>

                <form:label path="categories"><spring:message
                        code="article.createArticleForm.category"/></form:label>
                <div class="category-list-container my-2 mx-1">
                    <c:forEach items="${categories}" var="category">
                        <div class="category-item">
                            <form:checkbox cssClass="list-element" path="categories" value="${category.id}"/>
                            <spring:message code="${category.description}"/>
                        </div>
                    </c:forEach>
                    <form:errors path="categories" element="p" cssClass="error"/>
                </div>

                <c:if test="${articleId == null}">
                    <h:imageInput multiple="multiple" path="files"/>
                </c:if>

                <div class="d-flex justify-content-end">
                    <button type="submit" class="rounded btn btn-dark bg-color-action">
                        <spring:message code="${confirmButton}"/>
                    </button>
                    <c:if test="${isEdit}">
                        <button type="button" class="btn btn-link mx-1">
                            <a class="color-danger" href="${goBack}"><spring:message
                                    code="account.form.cancelButton"/></a>
                        </button>
                    </c:if>
                </div>
            </div>
        </form:form>
    </div>
</div>
<h:footer/>
</body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
<script src="<c:url value="/resources/js/main.js" />" defer></script>
</html>
