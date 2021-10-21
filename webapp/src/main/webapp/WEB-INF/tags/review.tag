<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="control" tagdir="/WEB-INF/tags/controls" %>
<%@ attribute name="articleId" required="true" %>
<%@ attribute name="review" type="ar.edu.itba.paw.models.Review" required="true" %>
<%@ attribute name="userId" required="true" %>
<div>
    <c:url value="/review/${review.id}/edit" var="editReview"/>
    <div class="row align-items-center my-2">
        <h4 class="col-6 h4"><c:out
                value="${review.renter.firstName} ${review.renter.lastName}"/></h4>
        <h5 class="col-5 h5"><c:out value="${review.createdAt.toLocaleString()}"/></h5>
        <c:if test="${userId == review.renter.id}">
            <a class="col-1 fa-lg" href="${editReview}"><i class="color-action bi bi-pencil-fill"></i></a>
        </c:if>
    </div>
    <h:rating rating="${review.rating}"/>
    <p class="text-muted mt-2"><c:out value="${review.message}"/></p>
    <hr/>
</div>