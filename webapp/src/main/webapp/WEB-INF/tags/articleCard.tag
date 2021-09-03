<%@ tag language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ attribute name="title" required="true" %>
<%@ attribute name="description" required="true" %>
<%@ attribute name="id" required="true" %>

<html>
<head>
    <link href="<c:url value="/resources/css/main.css" />" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <title>Article Card</title>
</head>
<body>
<div class="card text-dark bg-light"
     style="width: 18rem; margin: 3px;box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 10px 0 rgba(0, 0, 0, 0.19)">
    <%--    <img src="..." class="card-img-top" alt="...">--%>
    <div class="card-body">
        <h5 class="card-title"><c:out value="${title}"/></h5>
        <p class="card-text"><c:out value="${description}"/></p>
        <a href="article/${id}" class="btn btn-primary">View</a>
    </div>
</div>
</body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
<script src="<c:url value="/resources/js/main.js" />" defer></script>
</html>