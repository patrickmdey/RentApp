<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<c:url value="/create-article" var="articleUrl"/>
<html>
<h:head title="Publish Form"/>
<body class="bg-color-grey">
<h:navbar loggedUserId="${user.id}"/>
<div class="main-container">
    <div class="card shadow card-style create-card mx-3">
        <form:form modelAttribute="createArticleForm" action="${articleUrl}" method="post"
                   enctype="multipart/form-data">
            <div class="form-container">
                <h3 class="h3 fw-bold my-2"><spring:message code="article.createArticleForm.title"/></h3>
                <hr/>
                <div class="form-input">
                    <form:label path="name"><spring:message code="article.createArticleForm.name"/></form:label>
                    <spring:message code="article.createArticleForm.name.input" var="articleName"/>
                    <form:input type="text" path="name" class="form-control form-control-custom"
                                placeholder="${articleName}"/>
                    <form:errors path="name" element="p" cssClass="error">
                        <spring:message code="article.createArticleForm.name.error"/>
                    </form:errors>
                </div>
                <div class="col-12 form-input">
                    <form:label path="description"><spring:message
                            code="article.createArticleForm.description"/></form:label>
                    <spring:message code="article.createArticleForm.description.input" var="articleDescription"/>
                    <form:textarea type="text" path="description" class="form-control form-control-custom"
                                   placeholder="${articleDescription}"/>
                    <form:errors path="description" element="p" cssClass="error">
                        <spring:message code="article.createArticleForm.description.error"/>
                    </form:errors>
                </div>
                <div class="col-12 form-input">
                    <form:label path="pricePerDay"><spring:message code="article.createArticleForm.price"/></form:label>
                    <form:input type="number" path="pricePerDay" class="form-control form-control-custom"/>
                    <form:errors path="pricePerDay" element="p" cssClass="error"/>
                </div>
                <form:errors path="pricePerDay" element="p" cssClass="error">
                    <spring:message code="article.createArticleForm.price.error"/>
                </form:errors>


                <form:label path="categories"><spring:message
                        code="article.createArticleForm.category"/></form:label>
                <div class="category-list-container my-2 mx-1">
                    <c:forEach items="${categories}" var="category">
                        <form:checkbox cssClass="list-element" path="categories" value="${category.id}"/>
                        <c:out value="${category.description}"/>
                    </c:forEach>
                    <form:errors path="categories" element="p" cssClass="error">
                        <spring:message code="article.createArticleForm.category.error"/>
                    </form:errors>
                </div>

                <h:imageInput multiple="multiple"/>

                <button type="submit" class="rounded btn btn-primary"><spring:message
                        code="article.createArticleForm.publish"/></button>
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
