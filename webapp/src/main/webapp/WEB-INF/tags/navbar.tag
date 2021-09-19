<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url value="/" var="marketplace"/>
<c:url value="/create-article" var="createArticle"/>
<%@ attribute type="ar.edu.itba.paw.models.User" name="loggedUser" required="false" %>
<c:url value="/user/view" var="viewUser"/>
<c:url value="/user/edit" var="editUser"/>
<c:url value="/user/login" var="login"/>
<c:url value="/user/logout" var="logout"/>
<c:url value="/user/register" var="register"/>

<c:url value="/user/my-requests" var="myAccount"/>

<nav class="navbar navbar-expand-lg navbar-dark bg-color-primary mb-3">
    <div class="container-fluid">
        <a class="navbar-brand" href="${marketplace}">
            <img src="<c:url value="/resources/image/rentapp-logo.png"/>" height="50px" alt="RentApp">
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup"
                aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse mt-2" id="navbarNavAltMarkup">
            <div class="navbar-nav">
                <a class="nav-link active" href="${createArticle}">Publish Article</a>
                <a class="nav-link active" aria-current="page" href="${marketplace}">Marketplace</a>

                <c:choose>
                    <c:when test="${loggedUser != null}">
                        <div class="dropdown">
                            <a class="nav-link active color-grey dropdown-toggle" id="accountMenu"
                               data-bs-toggle="dropdown" aria-expanded="false">
                                <span>${loggedUser.firstName}</span>
                                <img src="${loggedUser.photo}" width="60px" height="60px">
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="accountMenu">
                                <li><a class="dropdown-item" href="${myAccount}">Mis solicitudes</a></li>
                                <li><a class="dropdown-item" href="${viewUser}">Mi perfil</a></li>
                                <li><a class="dropdown-item" href="#">Cerrar sesión</a></li>
                            </ul>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <a class="nav-link active" aria-current="page" href="${login}">LogIn</a>
                        <a class="nav-link active" aria-current="page" href="${register}">SignUp</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</nav>
