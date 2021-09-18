<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ attribute name="mode" required="true" type="java.lang.String" %>
<%@ attribute name="visible" required="true" type="java.lang.Boolean" %>
<%@ attribute name="messages" required="true" type="java.util.List<java.lang.String>"
        description="It contains the code of the resources that contain the message"
%>



<c:set var="isSuccess" value="${mode == \"success\"}"/>
<c:set var="isError" value="${mode == \"error\"}"/>
<c:set var="isWarning" value="${mode == \"warning\"}"/>

<c:if test="${isSuccess}">
    <c:set var="color" value="bg-success"/>
</c:if>
<c:if test="${isError}">
    <c:set var="color" value="bg-danger"/>
</c:if>
<c:if test="${isWarning}">
    <c:set var="color" value="bg-warning"/>
</c:if>

<html>
<body>
<c:if test="${visible}">
    <div class="card text-white container ${color}">
        <div class="card-body">

        <h5 class="card-title">
            <c:if test="${isSuccess}">
                <spring:message code="messagePanel.title.success"/>
            </c:if>
            <c:if test="${isError}">
                <spring:message code="messagePanel.title.error"/>
            </c:if>
            <c:if test="${isWarning}">
                <spring:message code="messagePanel.title.warning"/>
            </c:if>
        </h5>

            <ul>
                <c:forEach items="${messages}" var="msg">
                    <li>
                        <spring:message code="${msg}"/>
                    </li>
                </c:forEach>
            </ul>
        </div>

    </div>
</c:if>
</body>
</html>