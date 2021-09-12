<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url value="/marketplace" var="marketplace"/>
<c:url value="/marketplace/create-article" var="createArticle"/>

<c:url value="/user/view" var="viewUser"/>
<c:url value="/user/edit" var="editUser"/>


<html>
<body>
<nav class="navbar navbar-expand-lg navbar-dark mb-3 nav-bar-color">
    <div class="container">
        <a class="navbar-brand" href="${marketplace}">RentIt</a>
        <a href="${createArticle}">Add Article<i class="bi-plus"></i></a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" href="${marketplace}">Marketplace</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" href="${editUser}">editUser</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" href="${viewUser}">viewUser</a>
                </li>
            </ul>
            <form class="d-flex" action="${marketplace}" method="get">
                <input class="form-control me-2 my-auto" id="name" name="name" type="search" placeholder="Search"
                       aria-label="Search">
                <button class="btn btn-outline-success bg-light my-auto" type="submit">Search</button>
            </form>
        </div>
    </div>
</nav>
</body>
</html>