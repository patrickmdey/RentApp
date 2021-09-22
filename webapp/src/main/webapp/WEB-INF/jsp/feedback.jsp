<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>
<c:url value="/" var="marketplace"/>
<c:set value="https://icons-for-free.com/iconfiles/png/512/complete+done+green+success+valid+icon-1320183462969251652.png"
       var="successIcon"/>
<html>
<h:head title="Solicitud Enviada"/>
<body class="bg-color-grey">
<h:navbar loggedUser="${user}"/>
<div class="main-container">
    <div class="card shadow card-style">
        <div class="d-flex justify-content-center">
            <img src="${successIcon}" height="280px" width="280px" alt="success">
        </div>
        <h2 class="h2">
            <spring:message code="feedback.message"/>
        </h2>
        <a class="lead" href="${marketplace}"><spring:message code="feedback.moreArticles"/></a>
    </div>
</div>
<h:footer/>
</body>
</html>
