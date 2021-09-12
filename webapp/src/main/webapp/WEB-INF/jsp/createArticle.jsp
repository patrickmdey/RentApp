<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<c:url value="/create-article" var="articleUrl"/>
<html>
<h:head title="Publish Form"/>
<body class="bg-color-grey">
<h:navbar/>
<div class="main-container container-height">
    <div class="card shadow card-style create-card mx-3">
        <form:form modelAttribute="createArticleForm" action="${articleUrl}" method="post">
            <div class="form-container">
                <h3 class="h3 fw-bold my-2"><spring:message code="create-article.title"/></h3>
                <div class="form-input">
                    <spring:message code="article.rentRequestForm.name" var="articleName"/>
                    <form:input type="text" path="name" class="form-control form-control-custom"
                                placeholder="${articleName}"/>
                    <form:errors path="name" element="p" cssStyle="color: red"/>
                </div>
                <div class="form-input">
                    <spring:message code="article.createArticleForm.description" var="description"/>
                    <form:input type="text" path="description" class="form-control form-control-custom"
                                placeholder="${description}"/>
                    <form:errors path="description" element="p" cssStyle="color: red">
                        <spring:message code="article.createArticleForm.description.error"/>
                    </form:errors>
                </div>

                <p class="text-muted"><spring:message code="article.createArticleForm.price"/></p>
                <div class="form input-group">
                    <span class="input-group-text">$</span>
                    <form:input type="number" path="pricePerDay" class="form-control form-control-custom"/>
                </div>
                <form:errors path="pricePerDay" element="p" cssStyle="color: red">
                    <spring:message code="article.createArticleForm.price.error"/>
                </form:errors>


            </div>


            <div class="category-list-container my-2">
                <ul class="list-elements">
                    <c:forEach var="category" items="${categories}">
                        <form:button
                                class="btn btn-sm btn-outline-primary text-dark enable-rounded ma-1">${category.description}</form:button>
                    </c:forEach>
                </ul>
            </div>
            <div class="form-container">
                <div class="button">
                    <button type="submit" class="rounded btn btn-primary"><spring:message
                            code="article.createArticleForm.publish"/></button>
                </div>
            </div>

        </form:form>
    </div>
</div>
<h:footer/>
</body>
<h:mainScript/>
</html>
