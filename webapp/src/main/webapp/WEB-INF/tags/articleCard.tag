<%@ tag language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ attribute name="title" required="true" %>
<%@ attribute name="price" required="true" %>
<%@ attribute name="id" required="true" %>

<html>
<body>
<div class="card card-style text-dark bg-light">

    <img src="https://www.sinrumbofijo.com/wp-content/uploads/2016/05/default-placeholder.png" class="card-image"
         alt="${title} image}">


    <div class="card-title-container">
        <h5 class="h5 font-weight-bold">
            <c:out value="${title}"/>
        </h5>
    </div>
    <div class="card-content">

        <div class="card-price-container">
            <p class="card-text lead article-price mt-2"><spring:message code="article.price"
                                                                    arguments="${price}"/></p>
        </div>
        <div class="card-location-container">
            <p class="article-location lead"><i class="bi-geo-alt-fill"></i>Location</p>

        </div>
    </div>
    <a href="article/${id}" class="stretched-link"></a>
</div>
</body>
</html>