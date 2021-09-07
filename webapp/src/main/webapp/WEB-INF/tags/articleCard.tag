<%@ tag language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ attribute name="title" required="true" %>
<%@ attribute name="price" required="true" %>
<%@ attribute name="id" required="true" %>

<html>
<body>
<div class="card card-style text-dark bg-light">
    <img src="https://www.sinrumbofijo.com/wp-content/uploads/2016/05/default-placeholder.png" class="card-img-top"
         alt="...">
    <div class="card-body">
        <h5 class="card-title font-weight-bold">
            <c:out value="${title}"/>
        </h5>
        <div class="card-content">
            <p class="card-text lead article-price"><spring:message code="article.price"
                                                                    arguments="${price}"/></p>
            <p class="article-location lead"><i class="bi-geo-alt-fill"></i>Location</p>
        </div>
        <a href="article/${id}" class="stretched-link"></a>
    </div>
</div>
</body>
</html>