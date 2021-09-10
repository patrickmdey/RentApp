<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<c:url value="/create-article" var="articleUrl"/>
<html>
<h:head/>
<body class="article-background">
<h:navbar/>
<div class="main-container">
    <div class="card card-style">
        <h3 class="h3"><spring:message code="create-article.title"/></h3>
        <form:form modelAttribute="createArticleForm" action="${articleUrl}" method="post">
            <div class="row">
                <div class="col-12 form-input">
                    <form:label path="name"><spring:message code="article.form.name"/></form:label>
                    <form:input type="text" path="name" class="form-control form-control-custom"
                                placeholder="Article Name"/>
                    <form:errors path="name" element="p" cssClass="text-danger"/>
                </div>
                <div class="col-12 form-input">
                    <form:label path="description"><spring:message code="article.form.description"/></form:label>
                    <form:input type="text" path="description" class="form-control form-control-custom"
                                placeholder="Description"/>
                    <form:errors path="description" element="p" cssStyle="color: red"/>
                </div>
                <div class="col-12 form-input">
                    <form:label path="pricePerDay"><spring:message code="article.form.price"/></form:label>
                    <form:input type="number" path="pricePerDay" class="form-control form-control-custom"/>
                    <form:errors path="pricePerDay" element="p" cssStyle="color: red"/>
                </div>

                <div class="row justify-content-center">
                    <ul class="category-list">
                        <c:forEach var="category" items="${categories}">
                            <form:button
                                    class="btn btn-outline-secondary text-dark enable-rounded">${category.description}</form:button>
                        </c:forEach>
                    </ul>
                </div>
                <div class="col-12 justify-content-center">
                    <div class="button">
                        <button type="submit" class="rounded btn-primary"><spring:message
                                code="article.form.publish"/></button>
                    </div>
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
