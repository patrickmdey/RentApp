<%@ tag language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ attribute name="title" required="true" %>
<%@ attribute name="price" required="true" %>
<%@ attribute name="id" required="true" %>
<%@ attribute name="location" required="true" %>
<%@ attribute name="image_id" required="true" %>
<%@ attribute name="rating" required="true" %>
<%@ attribute name="timesReviewed" required="true" %>

<html>
<body>
<div class="card marketplace-card-style shadow-sm text-dark bg-light mb-4">

    <div class="marketplace-card-img-container">
        <img src="<c:url value="/image/${image_id}"/>" class="card-image"
             alt="<c:out value="${title}"/> - image">
    </div>

    <div class="marketplace-card-info-container">
        <div class="card-title-container">
            <h3 class="h3 text-truncate">
                <c:out value="${title}"/>
            </h3>
        </div>
        <div class="card-content">
            <h5 class="h5 color-action my-1"><i class="bi-geo-alt-fill"></i>
                <c:out value="${location}"/>
            </h5>
            <h:rating rating="${rating}" timesReviewed="${timesReviewed}"/>
            <h4 class="h4 mb-2 color-rentapp-red">
                <spring:message
                        code="article.price"
                        arguments="${price}"/>
            </h4>
        </div>
    </div>
    <a href="<c:url value="/article/${id}"/>" class="stretched-link"></a>
</div>
</body>
</html>