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
                     class="img-fluid rounded-start" width="100%" height="auto" alt="...">
            </div>
            <div class="col-md-1"></div>
            <div class="col-md-7">
                <div class="card-body">
                    <h2 class="card-title display-6">${article.title}</h2>
                    <p class="article-location"><i class="bi-geo-alt-fill"></i>${owner.location}</p>
                    <div class="row">
                        <ul class="category-list">
                            <c:forEach var="category" items="${article.categories}">
                                <h5><span class="badge bg-light text-dark">${category}</span></h5>
                            </c:forEach>
                        </ul>
                    </div>
                    <h4 class="card-text h4 article-price"><spring:message code="article.price"
                                                                           arguments="${article.pricePerDay}"/></h4>
                    <button type="button" class="btn btn-primary mt-2" data-bs-toggle="modal"
                            data-bs-target="#rentFormModal">
                        Rent request
                    </button>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal -->
    <div class="modal fade" id="rentFormModal" tabindex="-1"
         aria-labelledby="rentFormModal"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel"><spring:message
                            code="article.rentRequestTitle"/></h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body article-background">
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
                            <form:label path="startDate"><spring:message
                                    code="article.form.startDate"/></form:label>
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
                            <form:textarea name="message" path="message" class="form-control form-control-custom "
                                           placeholder="Your Message"/>
                            <form:errors path="message" element="p" cssStyle="color: red"/>
                        </div>
                    </div>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Close</button>
                    <form:button type="submit" class="btn btn-success"><spring:message
                            code="article.sendRequestButton"/></form:button>
                </div>
                </form:form>
            </div>
        </div>
    </div>


    <div class="row">
        <div class="col-lg-8 col-md-7 col-12">
            <div class="card card-style secondary-card">
                <h3 class="h3"><spring:message code="article.descriptionTitle"/></h3>
                <p class="lead">${article.description}</p>
            </div>
        </div>

        <div class="col-lg-4 col-md-5 col-12">
            <div class="card card-style secondary-card">
                <h3 class="h3"><spring:message code="article.ownerTitle"/></h3>
                <div class="row">
                    <img src="https://www.sinrumbofijo.com/wp-content/uploads/2016/05/default-placeholder.png"
                         style="max-height: 20px" alt="#">
                    <p class="lead">${owner.firstName} ${owner.lastName}</p>
                </div>
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
