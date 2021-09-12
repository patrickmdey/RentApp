<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<c:url value="/article/${articleId}" var="articleUrl"/>
<html>
<h:head title="${article.title}"/>
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
                                <c:url var="marketplaceUrl" value="/"><c:param name="category"
                                                                               value="${category.id}"/></c:url>
                                <h5><a href="${marketplaceUrl}"> <span
                                        class="badge bg-background-grey text-dark">${category.description}</span></a>
                                </h5>
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
                <form:form modelAttribute="rentForm" action="${articleUrl}" method="POST">

                    <div class="modal-header article-background">
                        <p class="modal-title lead fw-bold"><spring:message
                                code="article.rentRequestTitle"/></p>
                        <button type="button" class="btn-close btn-dark" data-bs-dismiss="modal"
                                aria-label="Close"></button>
                    </div>

                    <div class="modal-body article-background">
                        <div class="row">
                            <div class="col-12 form-input">
                                <form:label path="name"><spring:message
                                        code="article.rentRequestForm.name"/></form:label>
                                <spring:message code="article.rentRequestForm.name.input" var="enterName"/>
                                <form:input type="text" path="name" class="form-control form-control-custom"
                                            placeholder="${enterName}"/>
                                <form:errors path="name" element="p" cssClass="text-danger">
                                    <spring:message code="article.rentRequestForm.name.input.error"/>
                                </form:errors>
                            </div>
                            <div class="col-12 form-input">
                                <form:label path="email"><spring:message
                                        code="article.rentRequestForm.email"/></form:label>
                                <spring:message code="article.rentRequestForm.email.input" var="enterEmail"/>
                                <form:input type="email" path="email" class="form-control form-control-custom"
                                            placeholder="${enterEmail}"/>
                                <form:errors path="email" element="p" cssStyle="color: red">
                                    <spring:message code="article.rentRequestForm.email.input.error"/>
                                </form:errors>
                            </div>
                            <div class="col-6 form-input">
                                <form:label path="startDate"><spring:message
                                        code="article.rentRequestForm.startDate"/></form:label>
                                <form:input type="date" path="startDate" class="form-control form-control-custom"/>
                                <form:errors path="startDate" element="p" cssStyle="color: red">
                                    <spring:message code="article.rentRequestForm.startDate.input.error"/>
                                </form:errors>
                            </div>
                            <div class="col-6 form-input">
                                <form:label path="endDate"><spring:message
                                        code="article.rentRequestForm.endDate"/></form:label>
                                <form:input type="date" path="endDate" class="form-control form-control-custom"/>
                                <form:errors path="endDate" element="p" cssStyle="color: red">
                                    <spring:message code="article.rentRequestForm.endDate.input.error"/>
                                </form:errors>
                            </div>
                            <div class="col-12 form-input">
                                <form:label path="message"><spring:message
                                        code="article.rentRequestForm.message"/></form:label>
                                <spring:message code="article.rentRequestForm.message.input" var="enterMessage"/>
                                <form:textarea name="message" path="message" class="form-control form-control-custom "
                                               placeholder="${enterMessage}"/>
                                <form:errors path="message" element="p" cssStyle="color: red">
                                    <spring:message code="article.rentRequestForm.message.input.error"/>
                                </form:errors>
                            </div>
                        </div>

                    </div>
                    <div class="modal-footer article-background">
                        <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Close</button>
                        <button type="submit" class="btn btn-success"><spring:message
                                code="article.sendRequestButton"/></button>
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
