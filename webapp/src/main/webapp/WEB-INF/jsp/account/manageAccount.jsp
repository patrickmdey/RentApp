<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<h:head title="Manage Account"/>
<body class="bg-color-grey">
<h:navbar/>
<p>MANEJO DE CUENTA</p>

<c:forEach var="request" items="${requests}">
    <div style="border: 3px solid #EF6461; margin: 15px 0">
        <h1>Request</h1>
        <p class="lead">Desde: ${request.startDate}</p>
        <p class="lead">Hasta: ${request.endDate}</p>
        <p class="lead">Estado: ${request.approved}</p>
        <h3>Mensaje:</h3>
        <p class="lead">${request.message}</p>
    </div>
</c:forEach>

</body>
</html>