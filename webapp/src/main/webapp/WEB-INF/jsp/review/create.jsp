<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="control" tagdir="/WEB-INF/tags/controls" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:url value="/review/${articleId}/create" var="publishReview"/>
<c:url value="/article/${articleId}" var="goBack"/>

<html>
<h:head title="title.createReview"/>
<body class="bg-color-grey">
<h:navbar loggedUser="${user}"/>
<div class="main-container min-height">
    <div class="card shadow card-style create-card mx-3">
        <form:form modelAttribute="reviewForm" method="post" action="${publishReview}">
        <div class="form-container">
            <h3 class="h3 fw-bold my-1"><spring:message code="article.createReview.title"/></h3>
            <hr/>
            <div class="d-flex">
                <p class="lead fw-bold me-1"><spring:message code="article.writeReview.articleName"/></p>
                <p class="lead"><c:out value="${article.title}"/></p>
            </div>
            <p class="lead"><spring:message code="article.writeReview.rating"/></p>
            <div class="d-flex justify-content-center align-items-center">
                <c:forEach var="rate" items="${rating}">
                    <div class="mx-2">
                        <form:radiobutton path="rating" value="${rate}"/>
                        <c:out value="${rate}"/>
                    </div>
                </c:forEach>
                <form:errors path="rating"/>
            </div>

            <div class="form-group">
                <form:label path="message"><spring:message code="placeholder.review.message"/></form:label>
                <spring:message code="article.review.review" var="placeholder"/>
                <form:textarea path="message" type="text" placeholder="${placeholder}"
                               class="form-control form-control-custom"/>
                <form:errors path="message" element="p" cssClass="error"/>
            </div>
            <div class="d-flex justify-content-end">
                <a href="${goBack}" class="me-1 rounded btn btn-danger">
                    <spring:message code="account.form.cancelButton"/>
                </a>
                <button type="submit" class="rounded btn bg-color-action color-grey">
                    <spring:message code="account.form.publishButton"/>
                </button>
            </div>
        </div>
    </div>
    </form:form>
</div>
<h:footer/>
</body>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
<script src="<c:url value="/resources/js/main.js" />" defer></script>

</html>
