<%@ tag language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ attribute name="title" required="true" %>
<%@ attribute name="price" required="true" %>
<%@ attribute name="id" required="true" %>
<%@ attribute name="location" required="true" %>
<%@attribute name="image_id" required="true" %>

<html>
<body>
<div class="card marketplace-card-style shadow-sm text-dark bg-light">

    <div class="marketplace-card-img-container">
        <img src="<c:url value="/image/${image_id}"/>" class="card-image"
             alt="${title} image}">
    </div>


    <div class="marketplace-card-info-container">
        <div class="card-title-container">
            <p class="p lead font-weight-bold fw-normal text-truncate">
                <c:out value="${title}"/>
            </p>
        </div>
        <div class="card-content">
            <div class="card-location-container">
                <p class="color-secondary"><i class="bi-geo-alt-fill"></i><c:out value="${location}"/></p>
            </div>
            <div class="card-price-container">
                <p class="card-text lead article-price mt-2 fw-bold color-rentapp-red"><spring:message
                        code="article.price"
                        arguments="${price}"/></p>
            </div>
        </div>
    </div>
    <a href="article/${id}" class="stretched-link"></a>
</div>
</body>
</html>