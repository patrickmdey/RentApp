<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ attribute name="articles" type="java.util.List" required="true" %>
<%@ attribute name="maxPage" required="true" %>
<%@ attribute name="currentUrl" required="true" %>
<%@attribute name="outlined" required="true" type="java.lang.Boolean" %>
<div class="justify-content-center">
    <div class="row row-cols-3 justify-content-start container-height">
        <c:forEach var="article" items="${articles}">
            <div class="col">
                <h:marketplaceCard title="${article.title}" price="${article.pricePerDay}"
                                   id="${article.id}"
                                   location="${article.owner.location.name}"
                                   image_id="${article.images.size() == 0 ? 1 : article.images.get(0).id}"
                                   outlined="${outlined}"
                                   rating="${article.rating}"/>
            </div>
        </c:forEach>
    </div>
    <h:pagination currentUrl="${currentUrl}" maxPage="${maxPage}"/>
</div>