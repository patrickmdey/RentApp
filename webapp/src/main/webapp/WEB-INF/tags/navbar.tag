<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url value="/" var="marketplace"/>
<c:url value="/create-article" var="createArticle"/>
<%@ attribute name="loggedUserId" required="true" %>
<c:url value="/user/view" var="viewUser"/>
<c:url value="/user/edit" var="editUser"/>
<c:url value="/user/login" var="login"/>
<c:url value="/user/register" var="register"/>

<c:url value="/user/${loggedUserId}/my-account" var="myAccount"/>

<nav class="navbar shadow-sm navbar-expand-lg bg-color-primary navbar-dark py-3 nav-bar-style mb-3">
    <div class="container-fluid nav-bar">
        <a class="navbar-brand mb-1" href="${marketplace}">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
      integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">

<nav class="navbar navbar-expand-lg navbar-dark bg-color-primary">
    <div class="container-fluid">
        <a class="navbar-brand" href="${marketplace}">
            <img src="<c:url value="/resources/image/rentapp-logo.png"/>" height="50px" alt="RentApp">
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse mt-2" id="navbarNavAltMarkup">
            <div class="navbar-nav">
                <a class="nav-link active" href="${createArticle}">Publish Article</a>
                <a class="nav-link active" aria-current="page" href="${marketplace}">Marketplace</a>
                <a class="nav-link active" aria-current="page" href="${login}">LogIn</a>
                <a class="nav-link active" aria-current="page" href="${register}">SignUp</a>
            </div>

            <a href="${myAccount}">My account</a>
        </div>
    </div>
</nav>
