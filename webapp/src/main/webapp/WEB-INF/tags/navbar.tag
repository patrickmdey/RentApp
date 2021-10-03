<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:url value="/" var="marketplace"/>
<c:url value="/article/create" var="createArticle"/>

<%--
    TODO: Why is this an argument? we could just see if the "loggedUser" or "user" is added to the ModelAndView
        If it is not added, we can just have it as a default value.
--%>
<%@ attribute type="ar.edu.itba.paw.models.User" name="loggedUser" required="false" %>

<c:url value="/user/view" var="viewUser"/>
<c:url value="/user/edit" var="editUser"/>
<c:url value="/user/login" var="login"/>
<c:url value="/user/logout" var="logout"/>
<c:url value="/user/register" var="register"/>

<c:url value="/user/my-requests/pending" var="myAccount"/>

<nav class="navbar navbar-expand-lg navbar-dark bg-color-primary mb-3">
    <div class="container">
        <a class="navbar-brand" href="${marketplace}">
            <img src="<c:url value="/resources/image/rentapp-logo.png"/>" height="50px" alt="RentApp">
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup"
                aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse mt-2" id="navbarNavAltMarkup">
            <div class="navbar-nav">
                <a class="nav-link active" href="${createArticle}"><spring:message code="navbar.publishArticle"/></a>
                <a class="nav-link active" aria-current="page" href="${marketplace}"><spring:message code="navbar.marketplace"/></a>
            </div>
            <div class="navbar-nav ms-auto">
                <c:choose>
                    <c:when test="${loggedUser != null}">
                        <div class="dropdown">
                            <a class="nav-link active color-grey dropdown-toggle" id="accountMenu"
                               data-bs-toggle="dropdown" aria-expanded="false" role="button">
                                <span>
                                    <c:out value="${loggedUser.firstName}"/>
                                </span>
                                    <%--                                <img src="${loggedUser.photo}" width="60px" height="60px">--%>
                            </a>
                            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="accountMenu">
                                <li><a class="dropdown-item" href="${myAccount}"><spring:message
                                        code="dropdown.requests"/></a></li>
                                <li><a class="dropdown-item" href="${viewUser}"><spring:message
                                        code="dropdown.profile"/></a></li>
                                <li><a class="dropdown-item" href="${logout}"><spring:message
                                        code="dropdown.logout"/></a></li>
                            </ul>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <a class="nav-link active" aria-current="page" href="${login}"><spring:message code="navbar.login"/></a>
                        <a class="nav-link active" aria-current="page" href="${register}"><spring:message code="navbar.signup"/></a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</nav>
