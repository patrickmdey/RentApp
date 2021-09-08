<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<c:url value="/article/${articleId}" var="articleUrl"/>
<html>
<head>
    <link href="<c:url value="/resources/css/article.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/main.css" />" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
    <title>${article.title}</title>
</head>
<body class="article-background">
<h:navbar/>
<div class="main-container">
    <div class="card card-style">
        <div class="row g-0">
            <div class="col-md-4">
                <img src="https://www.sinrumbofijo.com/wp-content/uploads/2016/05/default-placeholder.png"
                     class="img-fluid rounded-start" alt="...">
            </div>
            <div class="col-md-1"></div>
            <div class="col-md-7">
                <div class="card-body">
                    <h2 class="card-title article-title">${article.title}</h2>
                    <p class="article-location"><i class="bi-geo-alt-fill"></i>${owner.location}</p>
                    <h4 class="card-text h4 article-price"><spring:message code="article.price"
                                                                           arguments="${article.pricePerDay}"/></h4>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-lg-8 col-md-7 col-12">
            <div class="card card-style">
                <h3 class="h3"><spring:message code="article.descriptionTitle"/></h3>
                <p class="lead">${article.description}</p>
            </div>

            <div class="card card-style">
                <h3 class="h3"><spring:message code="article.categoriesTitle"/></h3>
                <ul class="category-list">
                    <c:forEach var="category" items="${article.categories}">
                        <li>
                            <h1 class="badge badge-style bg-primary enable-rounded">${category}</h1>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>

        <div class="col-lg-4 col-md-5 col-12">
            <div class="card card-style">
                <h3 class="h3"><spring:message code="article.ownerTitle"/></h3>
                <div class="row">
                    <img src="https://www.sinrumbofijo.com/wp-content/uploads/2016/05/default-placeholder.png"
                         style="max-height: 20px" alt="#">
                    <p class="lead">${owner.firstName} ${owner.lastName}</p>
                </div>
            </div>

            <div class="card card-style">
                <h3 class="h3"><spring:message code="article.rentRequestTitle"/></h3>
                <form:form modelAttribute="rentForm" action="${articleUrl}" method="post">
                    <div class="row">
                        <div class="col-12 form-input">
                            <form:label path="name"><spring:message code="article.form.name"/></form:label>
                            <form:input type="text" path="name" class="form-control form-control-custom"
                                        placeholder="Your Name"/>
                            <form:errors path="name" element="p" cssClass="text-danger"/>
                        </div>
                        <div class="col-12 form-input">
                            <form:label path="email"><spring:message code="article.form.email"/></form:label>
                            <form:input type="email" path="email" class="form-control form-control-custom"
                                        placeholder="Your Email"/>
                            <form:errors path="email" element="p" cssStyle="color: red"/>
                        </div>
                        <div class="col-6 form-input">
                            <form:label path="startDate"><spring:message code="article.form.startDate"/></form:label>
                            <form:input type="text" path="startDate" class="form-control form-control-custom"/>
                            <form:errors path="startDate" element="p" cssStyle="color: red"/>
                        </div>
                        <div class="col-6 form-input">
                            <form:label path="endDate"><spring:message code="article.form.endDate"/></form:label>
                            <form:input type="text" path="endDate" class="form-control form-control-custom"/>
                            <form:errors path="endDate" element="p" cssStyle="color: red"/>
                        </div>
                        <div class="col-12 form-input">
                            <form:label path="message"><spring:message code="article.form.message"/></form:label>
                            <form:input name="message" path="message" class="form-control form-control-custom "
                                        placeholder="Your Message"/>
                            <form:errors path="message" element="p" cssStyle="color: red"/>
                        </div>
                        <div class="col-12 justify-content-center">
                            <div class="button">
                                <button type="submit" class="rounded btn-primary"><spring:message
                                        code="article.sendRequestButton"/></button>
                            </div>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>
</body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
<script src="<c:url value="/resources/js/main.js" />" defer></script>
</html>
