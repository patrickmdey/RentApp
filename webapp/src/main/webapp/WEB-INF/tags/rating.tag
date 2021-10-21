<%@ attribute name="rating" type="java.lang.Long" required="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${rating > 0}">
    <div class="d-flex align-items-start mt-2 mb-2">
        <c:forEach begin="1" end="${rating}">
            <i class="bi bi-star-fill color-rentapp-red fa-lg"></i>
        </c:forEach>
        <c:if test="${rating <5}">
            <c:forEach begin="1" end="${5-rating}">
                <i class="bi bi-star color-rentapp-black fa-lg"></i>
            </c:forEach>
        </c:if>
    </div>
</c:if>