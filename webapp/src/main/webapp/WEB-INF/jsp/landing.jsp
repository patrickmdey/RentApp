<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:url value="/" var="marketplace"/>
<c:url value="/user/my-requests/pending" var="myRequestsUrl"/>
<c:url value="/landing" var="currentUrl"/>
<c:url var="landingIllustration"
       value="https://previews.123rf.com/images/emojoez/emojoez1808/emojoez180800011/112266418-illustrations-concept-small-people-creating-value-of-partner-business-via-handshake-deal-between-com.jpg"
/>

<c:url value="https://img.freepik.com/vector-gratis/ilustracion-concepto-escoger_114360-553.jpg?size=338&ext=jpg&ga=GA1.2.1931844515.1631145600"
       var="decideImg"/>
<c:url var="selectImg"
       value="https://thumbs.dreamstime.com/b/happy-man-mobile-shopping-choose-product-goods-smartphone-give-rating-feedback-vector-173000676.jpg"/>
<html>
<h:head title="title.main"/>
<body>
<h:navbar loggedUser="${user}"/>
<div class="d-flex flex-column align-items-center">
    <div class="row main-margins landing-title-container">
        <div class="col-1"></div>
        <div class="col-5">
            <c:choose>
                <c:when test="${user != null && !user.type.isOwner}">
                    <h1 class="h1"><spring:message code="landing.title.withUser" arguments="${user.firstName}"/></h1>
                </c:when>
                <c:when test="${user != null && user.type.isOwner}">
                    <h1 class="h1"><spring:message code="landing.title.withUser" arguments="${user.firstName}"/></h1>
                    <c:choose>
                        <c:when test="${user.pendingRequestAmount > 0}">
                            <c:choose>
                                <c:when test="${user.pendingRequestAmount == 1}">
                                    <p class="lead"><spring:message code="landing.pendingRequests.single"/></p>
                                </c:when>
                                <c:otherwise>
                                    <p class="lead"><spring:message code="landing.pendingRequests.multiple"
                                                                    arguments="${user.pendingRequestAmount}"/></p>
                                </c:otherwise>
                            </c:choose>
                            <div class="d-grid gap-2 text-center">
                                <a href="${myRequestsUrl}" class="btn bg-color-action color-grey"><spring:message
                                        code="landing.viewRequests"/></a>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <p class="lead"><spring:message code="landing.pendingRequests.none"/></p>
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <h1 class="h1"><spring:message code="landing.title"/></h1>
                    <p class="lead"><spring:message code="landing.description"/></p>
                    <div class="d-grid gap-2 text-center">
                        <a href="${marketplace}" class="btn bg-color-action color-grey"><spring:message
                                code="landing.viewArticles"/></a>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="col-5 d-flex justify-content-center align-items-center">
            <div class="avatar-container landing-avatar">
                <img src="${selectImg}" width="200px" height="auto">
            </div>
            <div class="avatar-container landing-avatar">
                <img src="${decideImg}" width="200px" height="auto">
            </div>
            <div class="avatar-container landing-avatar">
                <img src="<c:url value="/resources/image/rentapp-favicon.png"/>" width="170px" height="auto">
            </div>
        </div>
        <div class="col-1"></div>
    </div>

    <div class="bg-color-grey w-100">
        <div class="main-margins p-4 justify-content-center">
            <h3><spring:message code="landing.articlesTitle"/></h3>
            <hr>
            <div class="row row-cols-4 justify-content-center w-100">
                <c:forEach var="article" items="${topRatingArticles}">
                    <div class="col">
                        <h:marketplaceCard title="${article.title}" price="${article.pricePerDay}"
                                           id="${article.id}"
                                           location="${article.owner.location.name()}"
                                           image_id="${article.images.size()==0 ? 1 : article.images.get(0).id}"
                                           rating="${article.rating}" timesReviewed="${article.timesReviewed}"/>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
    <div class="bg-color-secondary w-100">
        <h3 class="h3 text-bold text-center mt-2">Buscar por categorías</h3>
        <div class="row row-cols-7 justify-content-center align-items-center landing-category-container">
            <c:forEach items="${categories}" var="category">
                <div class="col">
                    <div class="card card-style category-card">
                        <img src="<c:url value="/image/${category.picture.id}"/>" width="100%" height="auto"
                             alt="${category.description}">
                        <div class="text-center mt-2">
                            <p class="lead"><spring:message code="${category.description}"/></p>
                        </div>
                        <a href="<c:url value="/?user=&query=&category=${category.id}&orderBy=7&location="/>"
                           class="stretched-link"></a>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
    <div class="bg-color-grey w-100">
        <div class="main-margins p-4 justify-content-center">
            <h3><spring:message code="landing.articlesTitle"/></h3>
            <hr>
            <div class="row row-cols-4 justify-content-center w-100">
                <c:forEach var="article" items="${topRentedArticles}">
                    <div class="col">
                        <h:marketplaceCard title="${article.title}" price="${article.pricePerDay}"
                                           id="${article.id}"
                                           location="${article.owner.location.name()}"
                                           image_id="${article.images.size()==0 ? 1 : article.images.get(0).id}"
                                           rating="${article.rating}" timesReviewed="${article.timesReviewed}"/>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
<h:footer/>
</body>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
<script src="<c:url value="/resources/js/main.js" />" defer></script>
</html>
