<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="footer-style bg-color-primary">
    <div class="card-body row my-n4">
        <div class="col-4 footer-image-col">
            <img class="mt-n3" src="<c:url value="/resources/image/rentapp-favicon.png"/>" height="180px" width="auto"
                 alt="RentApp">
        </div>
        <div class="col-4">
            <ul class="col-6 list-unstyled">
                <h3 class="h3 fw-bold color-grey"><spring:message code="footer.team"/></h3>
                <li><p class="color-grey">Santos Rosati</p></li>
                <li><p class="color-grey">Matias Lombardi</p></li>
                <li><p class="color-grey">Patrick Dey</p></li>
                <li><p class="color-grey">Lucas Dell'Isola</p></li>
            </ul>
        </div>
        <div class="col-4">
            <ul class="list-unstyled">
                <li><h3 class="h3 fw-bold color-grey"><spring:message code="footer.contact"/></h3></li>
                <li><p class="lead color-grey"><spring:message code="footer.email"/>: <a
                        href="mailto:paw2021b3@gmail.com" class="color-secondary">paw2021b3@gmail.com</a>
                </p>
                </li>
                <li><h3 class="h3 fw-bold color-grey"><spring:message code="footer.language"/></h3></li>
                <li>
                    <div class="d-flex align-items-center">
                        <a href="?lang=es"><spring:message code="language.spanish"/></a>
                        <a href="?lang=en"><spring:message code="language.english"/></a>
                    </div>
                </li>
            </ul>
        </div>
    </div>
    <div class="card-footer bg-color-secondary d-flex justify-content-center align-content-center my-n2">
        <p class="fw-light my-n2"><spring:message code="footer.subtitle"/></p>
    </div>
</div>
