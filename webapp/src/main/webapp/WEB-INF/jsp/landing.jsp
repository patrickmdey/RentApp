<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:url value="/" var="marketplace"/>
<c:url value="/landing" var="currentUrl"/>
<c:url var="landingIllustration"
       value="https://previews.123rf.com/images/emojoez/emojoez1808/emojoez180800011/112266418-illustrations-concept-small-people-creating-value-of-partner-business-via-handshake-deal-between-com.jpg"
/>

<c:url value="https://img.freepik.com/vector-gratis/ilustracion-concepto-escoger_114360-553.jpg?size=338&ext=jpg&ga=GA1.2.1931844515.1631145600"
       var="decideImg"/>
<c:url var="selectImg"
       value="https://thumbs.dreamstime.com/b/happy-man-mobile-shopping-choose-product-goods-smartphone-give-rating-feedback-vector-173000676.jpg"/>
<html>
<h:head title="title.createArticle"/>
<body>
<h:navbar loggedUser="${user}"/>
<div class="d-flex flex-column align-items-center">
    <div class="row bg-color-white main-margins p-4">
        <div class="col-1"></div>
        <div class="col-6">
            <h1 class="h1"><spring:message code="landing.title"/></h1>
            <p class="lead"><spring:message code="landing.description"/></p>
            <div class="d-grid gap-2 text-center">
                <a href="${marketplace}" class="btn bg-color-action color-grey"><spring:message
                        code="landing.viewArticles"/></a>
            </div>
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
    </div>

    <div class="bg-color-grey">
        <div class="row main-margins p-4">
            <div class="col-1"></div>
            <div class="col-10">
                <h3><spring:message code="landing.articlesTitle"/></h3>
                <hr>
                <h:allArticles articles="${articles}" maxPage="1" currentUrl="${currentUrl}" outlined="${false}"/>
            </div>
            <div class="col-1"></div>
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
