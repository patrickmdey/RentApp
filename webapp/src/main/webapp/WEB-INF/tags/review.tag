<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="control" tagdir="/WEB-INF/tags/Controls" %>
<%@ attribute name="articleId" required="true"%>
<%@ attribute name="review" type="ar.edu.itba.paw.models.Review" required="true"%>
<%@ attribute name="userId" required="true"%>
<div>
    <c:url value="/review/${review.id}/edit" var="editReview"/>
    <div class="row align-items-center">
        <h5 class="col-6 h5"><c:out
                value="${review.renter.firstName} ${review.renter.lastName}"/></h5>
        <p class="lead col-5"><c:out value="${review.createdAt.toLocaleString()}"/></p>
        <c:if test="${userId == review.renterId}">
            <a class="col-1 fa-lg" href="${editReview}"><i class="bi bi-pencil-fill"></i></a>
        </c:if>
    </div>
    <h:rating rating="${review.rating}"/>
    <p><c:out value="${review.message}"/></p>
    <hr/>
</div>