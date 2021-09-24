<%@ attribute name="rating" required="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="d-flex align-items-start mt-1 mb-2">
    <c:forEach begin="1" end="${rating}">
        <i class="bi bi-star-fill color-rentapp-red"></i>
    </c:forEach>
    <c:if test="${rating <5}">
        <c:forEach begin="1" end="${5-rating}">
            <i class="bi bi-star color-rentapp-black"></i>
        </c:forEach>
    </c:if>
</div>