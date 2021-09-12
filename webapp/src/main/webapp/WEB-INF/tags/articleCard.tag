<%@ tag language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ attribute name="title" required="true" %>
<%@ attribute name="price" required="true" %>
<%@ attribute name="id" required="true" %>
<%@ attribute name="location" required="true" %>

<html>
<body>
<div class="card card-style shadow-sm text-dark bg-light">

    <img src="https://www.sinrumbofijo.com/wp-content/uploads/2016/05/default-placeholder.png" class="card-image"
         alt="${title} image}">


    <div class="card-title-container">
        <p class="p lead font-weight-bold fw-normal">
            <c:out value="${title}"/>
        </p>
    </div>
    <div class="card-content">

        <div class="card-price-container">
            <p class="card-text lead article-price mt-2 fw-bold color-rentapp-red"><spring:message code="article.price"
                                                                                                   arguments="${price}"/></p>
        </div>
        <div class="card-location-container">
            <p class="my-2 color-secondary"><i class="bi-geo-alt-fill"></i><c:out value="${location}"/></p>
        </div>
    </div>
    <a href="article/${id}" class="stretched-link"></a>
</div>
</body>
</html>