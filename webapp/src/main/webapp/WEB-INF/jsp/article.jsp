<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="control" tagdir="/WEB-INF/tags/Controls" %>

<c:url value="/article/${articleId}" var="articleUrl"/>
<c:url value="/review/${articleId}/create" var="createReview"/>

<html>
<h:head title="${article.title}"/>
<body class="bg-color-grey">
<h:navbar loggedUser="${user}"/>
<div class="main-container">
    <div class="card card-style">
        <div class="row g-0">
            <div class="col-md-4 justify-content-ceter align-items-center">
                <img src="<c:url value="/image/${article.images.get(0)}"/>" id="main-img"
                     class="img-thumbnail rounded-start article-img" sizes="" alt="main image">
                <div class="d-flex flex-wrap">
                    <c:forEach var="image" items="${article.images}">
                        <button class="btn btn-link" onclick="setActiveImage('<c:url value="/image/${image}"/>')">
                            <img src="<c:url value="/image/${image}"/>" width="30px" height="30px" alt="image">
                        </button>
                    </c:forEach>
                </div>
            </div>
            <div class="col-md-1"></div>
            <div class="col-md-7">
                <h2 class="card-title display-6"><c:out value="${article.title}"/></h2>
                <c:url var="locationFilterUrl" value="/">
                    <c:param name="location" value="${article.location.ordinal()}"/>
                </c:url>
                <p class="lead article-location color-action">
                    <a href="${locationFilterUrl}">
                        <i class="bi-geo-alt-fill"></i><c:out value="${article.location.name}"/>
                    </a>
                </p>
                <div class="d-flex">
                    <c:forEach var="category" items="${article.categories}">
                        <c:url var="marketplaceUrl" value="/">
                            <c:param name="category" value="${category.id}"/>
                        </c:url>
                        <h5>
                            <a href="${marketplaceUrl}">
                                <span class="badge bg-background-grey text-dark m-1">
                                    <spring:message code="${category.description}"/>
                                </span>
                            </a>
                        </h5>
                    </c:forEach>
                </div>
                <c:choose>
                    <c:when test="${reviews.size() == 0}">
                        <p class="lead"><spring:message code="article.noRating"/></p>
                    </c:when>
                    <c:otherwise>
                        <h:rating rating="${articleRating}"/>
                    </c:otherwise>
                </c:choose>
                <h4 class="card-text h4 color-rentapp-red"><spring:message
                        code="article.price"
                        arguments="${article.pricePerDay}"/></h4>
                <c:choose>
                    <c:when test="${user == null}">
                        <a class="btn color-grey bg-color-action color-grey mt-2"
                           href="<c:url value="/user/login"/>"><spring:message code="article.rent"/></a>
                    </c:when>
                    <c:when test="${user.id == article.idOwner}">
                        <a class="btn color-grey bg-color-primary color-grey mt-2"
                           href="<c:url value="/article/${articleId}/edit"/>"><spring:message code="article.edit"/></a>
                    </c:when>
                    <c:otherwise>
                        <button type="button" class="btn color-grey bg-color-action mt-2" data-bs-toggle="modal"
                                data-bs-target="#rentFormModal">
                            <spring:message code="article.rent"/>
                        </button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>

    <!-- Modal -->
    <div class="modal fade" id="rentFormModal" tabindex="-1"
         aria-labelledby="rentFormModal"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content bg-color-grey">
                <form:form modelAttribute="rentForm" action="${articleUrl}" method="POST">

                    <div class="modal-header bg-color-grey">
                        <p class="modal-title lead fw-bold"><spring:message
                                code="article.rentRequestTitle"/></p>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                aria-label="Close"></button>
                    </div>

                    <div class="modal-body bg-color-grey">
                        <div class="row">
                            <div class="col-6 my-2">
                                <form:label path="startDate"><spring:message
                                        code="article.rentRequestForm.startDate"/></form:label>
                                <form:input type="date" path="startDate" class="form-control form-control-custom"/>
                            </div>
                            <div class="col-6 my-2">
                                <form:label path="endDate"><spring:message
                                        code="article.rentRequestForm.endDate"/></form:label>
                                <form:input type="date" path="endDate" class="form-control form-control-custom"/>
                                <form:errors path="endDate" element="p" cssClass="error">
                                    <spring:message code="errors.requiredEndDate"/>
                                </form:errors>
                            </div>
                            <div class="col-12">
                                <form:errors element="p" cssClass="color: #EF6461" path="startDate">
                                </form:errors>
                            </div>
                            <div class="col-12 my-2">
                                <form:label path="message"><spring:message
                                        code="article.rentRequestForm.message"/></form:label>
                                <spring:message code="placeholder.message" var="enterMessage"/>
                                <form:textarea name="message" path="message" class="form-control form-control-custom "
                                               placeholder="${enterMessage}"/>
                                <form:errors path="message" element="p" cssClass="error">
                                    <spring:message code="errors.requiredMessage"/>
                                </form:errors>
                            </div>
                        </div>

                    </div>
                    <div class="modal-footer bg-color-grey">
                        <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Close</button>
                        <form:button type="submit" class="btn btn-success"><spring:message
                                code="article.sendRequestButton"/></form:button>
                    </div>
                </form:form>
            </div>
        </div>
    </div>


    <div class="row w-100 g-0 justify-content-between">
        <div class="col-md-8 col-12 pe-md-3 pe-0">
            <div class="card card-style">
                <h3 class="h3"><spring:message code="article.descriptionTitle"/></h3>
                <hr/>
                <p class="lead"><c:out value="${article.description}"/></p>
            </div>

            <%--            Reviews--%>
            <div class="card card-style">
                <h:allReviews article="${article}" user="${user}"
                              hasRented="${hasRented}"
                              maxPage="${maxPage}" reviews="${reviews}"/>
            </div>
        </div>


        <div class="col-md-4 col-12">
            <div class="card card-style">
                <h3 class="h3"><spring:message code="article.ownerTitle"/></h3>
                <div class="row">
                    <div class="avatar-container col-5">
                        <img src="<c:url value="/image/${owner.picture}"/>"
                             alt="<c:out value="${owner.firstName}"/> picture"/>
                    </div>
                    <span class="lead col-5"><c:out value="${owner.firstName} ${owner.lastName}"/></span>

                    <c:url var="userFilterUrl" value="/">
                        <c:param name="user" value="${owner.id}"/>
                    </c:url>

                    <a class="navbar-brand" href="${userFilterUrl}"><spring:message code="article.view.viewMore"/></a>
                </div>
            </div>
        </div>
    </div>

    <!-- RECOMMENDED ARTICLES-->
    <c:if test="${recommended.size() > 0}">
        <h1 class="d-block w-100"><spring:message code="article.relatedTitle"/></h1>
        <div class="row row-cols-4 justify-content-start w-100">
            <c:forEach var="article" items="${recommended}">
                <div class="col">
                    <h:marketplaceCard title="${article.title}" price="${article.pricePerDay}"
                                       id="${article.id}"
                                       location="${article.location}"
                                       image_id="${article.images.size()==0 ? 1 : article.images.get(0)}"/>
                </div>
            </c:forEach>
        </div>
    </c:if>

</div>
<h:footer/>
</body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
<script>
    const htmlImg = document.getElementById("main-img");
    const baseURL = window.location.href.split('article/')[0] + "image/";

    function setActiveImage(src) {
        console.log(src);
        htmlImg.setAttribute("src", src);
    }
</script>
<script src="<c:url value="/resources/js/main.js"/>" defer></script>

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
<script>
    <c:if test="${requestFormHasErrors}">
    $(document).ready(function () {
        $('#rentFormModal').modal('show');
    });
    </c:if>
</script>

</html>
