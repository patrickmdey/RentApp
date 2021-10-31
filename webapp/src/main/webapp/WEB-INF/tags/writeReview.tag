<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="control" tagdir="/WEB-INF/tags/controls" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@attribute name="article" required="true" type="ar.edu.itba.paw.models.Article" %>
<%@attribute name="rating" required="true" type="java.lang.Integer" %>
<%@attribute name="reviewId" required="false" %>
<%@attribute name="isEdit" required="true" type="java.lang.Boolean" %>

<!-- TODO No funciona el pasaje de rating. Stackoverflow dice que es algo de hibernate.
        Dejamos review/create y review/edit como esta o tratamos de usar este .tag? -->

<c:choose>
    <c:when test="${isEdit}">
        <c:url value="/review/${reviewId}/edit" var="confirmReview"/>
    </c:when>
    <c:otherwise>
        <c:url value="/review/${article.id}/create" var="confirmReview"/>
    </c:otherwise>
</c:choose>

<c:url value="/article/${article.id}" var="goBack"/>

<div class="card shadow card-style create-card mx-3">
    <form:form modelAttribute="reviewForm" method="post" action="${confirmReview}">
        <div class="form-container">
            <h3 class="h3 fw-bold my-1"><spring:message code="article.createReview.title"/></h3>
            <hr/>
            <div class="row">
                <div class="col-4">
                    <img src="<c:url value="/image/${article.images.get(0).id}"/>" class="card-image"
                         alt="<c:out value="${article.title}"/> - image">
                </div>
                <div class="col-8">
                    <div class="d-flex justify-content-center">
                        <p class="lead fw-bold me-1"><spring:message code="article.writeReview.articleName"/></p>
                        <p class="lead fw-bold"><c:out value="${article.title}"/></p>
                    </div>
                    <div class="justify-content-center mt-3">
                        <p class="lead fw-bold"><spring:message code="article.writeReview.rating"/></p>
                        <div class="d-flex justify-content-center align-items-center">
                            <c:forEach var="rate" items="${rating}">
                                <div class="mx-2">
                                    <form:radiobutton path="rating" value="${rate}"/>
                                    <c:out value="${rate}"/>
                                </div>
                            </c:forEach>
                            <form:errors path="rating"/>
                        </div>
                    </div>
                </div>
            </div>

            <div class="form-group">
                <form:label path="message"><spring:message code="placeholder.review.message"/></form:label>
                <spring:message code="article.review.review" var="placeholder"/>
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