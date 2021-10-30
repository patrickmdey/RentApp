<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:url value="/" var="landingPage"/>
<c:url value="/marketplace" var="marketplace"/>
<c:url value="/article/create" var="createArticle"/>

<%@ attribute type="ar.edu.itba.paw.models.User" name="loggedUser" required="true" %>

<c:url value="/user/view" var="viewUser"/>
<c:url value="/user/edit" var="editUser"/>
<c:url value="/user/login" var="login"/>
<c:url value="/user/logout" var="logout"/>
<c:url value="/user/register" var="register"/>

<c:url value="/user/my-requests/pending" var="myAccount"/>

<nav class="navbar navbar-expand-lg navbar-dark bg-color-primary mb-3">
    <div class="container">
        <a class="navbar-brand" href="${landingPage}">
            <img src="<c:url value="/resources/image/rentapp-logo.png"/>" height="50px" alt="RentApp">
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup"
                aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse mt-2" id="navbarNavAltMarkup">

            <div class="navbar-nav ms-auto">
                <a class="nav-link active h6" href="${createArticle}"><spring:message
                        code="navbar.publishArticle"/></a>
                <a class="nav-link active h6" aria-current="page" href="${marketplace}"><spring:message
                        code="navbar.marketplace"/></a>
                <c:choose>
                    <c:when test="${loggedUser != null}">
                        <div class="dropdown">
                            <a class="nav-link active color-grey dropdown-toggle h6" id="accountMenu"
                               data-bs-toggle="dropdown" aria-expanded="false" role="button">
                                <span><c:out value="${loggedUser.firstName}"/></span>
                                <c:if test="${user.pendingRequestAmount > 0 || user.acceptedRequestAmount > 0}">
                                    <span class="badge bg-color-rentapp-red ms-1">${user.pendingRequestAmount + user.acceptedRequestAmount}</span>
                                </c:if>
                            </a>
                            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="accountMenu">
                                <li>
                                    <a class="dropdown-item align-items-center" href="${myAccount}">
                                        <span><i class="bi bi-inbox-fill"></i></span>
                                        <spring:message code="dropdown.requests"/>
                                        <c:if test="${user.pendingRequestAmount > 0 || user.acceptedRequestAmount > 0}">
                                            <span class="badge bg-color-rentapp-red ms-1">${user.pendingRequestAmount + user.acceptedRequestAmount}</span>
                                        </c:if>
                                    </a>
                                </li>
                                <li>
                                    <a class="dropdown-item" href="${viewUser}">
                                        <span><i class="bi bi-person-fill"></i></span>
                                        <spring:message code="dropdown.profile"/>
                                    </a>
                                </li>
                                <li>
                                    <a class="dropdown-item" href="${logout}">
                                        <span><i class="bi bi-box-arrow-in-left"></i></span>
                                        <spring:message code="dropdown.logout"/>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <a class="nav-link active h6" aria-current="page" href="${login}"><spring:message
                                code="navbar.login"/></a>
                        <a class="nav-link active h6" aria-current="page" href="${register}"><spring:message
                                code="navbar.signup"/></a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</nav>
