<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="control" tagdir="/WEB-INF/tags/controls" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:url value="/review/${reviewId}/edit" var="updateReview"/>
<c:url value="/article/${article.id}" var="goBack"/>

<!-- TODO Este archivo es igual al review/create. MODULARIZAR -->
<html>
<h:head title="title.editReview"/>
<body class="bg-color-grey">
<h:navbar loggedUser="${user}"/>
<div class="main-container min-height">
    <div class="card shadow card-style create-card mx-3">
        <form:form modelAttribute="reviewForm" method="post" action="${updateReview}">
            <div class="form-container">
                <h3 class="h3 fw-bold my-1"><spring:message code="article.editReview.title"/></h3>
                <hr/>
                <div class="row">
                    <div class="col-4">
                        <img src="<c:url value="/image/${article.images.get(0).id}"/>" class="card-image"
                             alt="<c:out value="${article.title}"/> - image">
                    </div>
                    <div class="col-8">
                        <div>
                            <p class="lead fw-bold"><c:out value="${article.title}"/></p>
                        </div>
                        <div class="mt-3">
                            <p class="lead"><spring:message code="article.writeReview.rating"/></p>
                            <div class="d-flex justify-content-between align-items-center w-100">
                                <c:forEach var="rate" items="${rating}">
                                    <div class="mx-2 mb-2">
                                        <form:radiobutton path="rating" value="${rate}"/>
                                        <c:out value="${rate}"/>
                                    </div>
                                </c:forEach>
                            </div>
                            <form:errors path="rating" element="p" cssClass="error"/>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <form:label path="message"><spring:message code="article.review.review"/></form:label>
                    <spring:message code="placeholder.review.message" var="placeholder"/>
                    <form:textarea path="message" type="text" placeholder="${placeholder}"
                                   class="form-control form-control-custom"/>
                    <form:errors path="message" element="p" cssClass="error"/>
                </div>
                <div class="d-flex justify-content-end">
                    <button type="submit" class="rounded btn bg-color-action color-grey me-1">
                        <spring:message code="account.form.publishButton"/>
                    </button>
                    <a href="${goBack}" class="rounded btn btn-link color-danger">
                        <spring:message code="account.form.cancelButton"/>
                    </a>
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
