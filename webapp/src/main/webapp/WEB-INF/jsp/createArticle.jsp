<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<c:url value="/create-article" var="articleUrl"/>
<html>
<h:head title="Publish Form"/>
<body class="article-background">
<h:navbar/>
<div class="main-container">
    <div class="card shadow card-style create-card mx-3">
        <form:form modelAttribute="createArticleForm" action="${articleUrl}" method="post"
                   enctype="multipart/form-data">
            <div class="form-container">
                <h3 class="h3 fw-bold my-2"><spring:message code="create-article.title"/></h3>
                <div class="form-input">
                    <spring:message code="article.createArticleForm.name" var="articleName"/>
                    <form:input type="text" path="name" class="form-control form-control-custom"
                                placeholder="Article Name"/>
                    <form:errors path="name" element="p" cssClass="text-danger"/>
                </div>
                <div class="col-12 form-input">
                    <form:label path="description"><spring:message
                            code="article.createArticleForm.description"/></form:label>
                    <form:input type="text" path="description" class="form-control form-control-custom"
                                placeholder="Description"/>
                    <form:errors path="description" element="p" cssStyle="color: red"/>
                </div>
                <div class="col-12 form-input">
                    <form:label path="pricePerDay"><spring:message code="article.createArticleForm.price"/></form:label>
                    <form:input type="number" path="pricePerDay" class="form-control form-control-custom"/>
                    <form:errors path="pricePerDay" element="p" cssStyle="color: red"/>
                </div>
            </div>


            <div class="category-list-container my-2">
                <ul class="list-elements">
                    <c:forEach var="category" items="${categories}">
                        <form:button
                                class="btn btn-sm btn-outline-primary text-dark enable-rounded ma-1"
                                type="button">${category.description}</form:button>
                    </c:forEach>
                </ul>
            </div>

            <div class="form-container">
                <h:imageInput multiple="multiple"/>
            </div>
            <div class="form-container">
                <div class="button">
                    <button type="submit" class="rounded btn btn-primary"><spring:message
                            code="article.createArticleForm.publish"/></button>
                </div>
            </div>
        </form:form>
    </div>
</div>
</body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
<script src="<c:url value="/resources/js/main.js" />" defer></script>
</html>

