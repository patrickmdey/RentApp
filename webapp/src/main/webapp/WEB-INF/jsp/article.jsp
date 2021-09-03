<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<html>
<head>
    <link href="<c:url value="/resources/css/article.css" />" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
    <title>${article.title}</title>
</head>
<body style="background-color: #eaedea;">
<div class="main-container">
    <div class="card card-style">
        <div class="row g-0">
            <div class="col-md-4">
                <img src="https://www.sinrumbofijo.com/wp-content/uploads/2016/05/default-placeholder.png"
                     class="img-fluid rounded-start" alt="...">
            </div>
            <div class="col-md-7">
                <div class="card-body">
                    <h2 class="card-title article-title">${article.title}</h2>
                    <p class="article-location"><i class="bi-geo-alt-fill"></i>${owner.location}</p>
                    <p class="card-text article-price">$${article.pricePerDay} per day</p>
                    <p class="card-text">${article.description}</p>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-lg-8 col-md-7 col-12">
            <div class="card card-style">
                <h3>Description</h3>
                <p>${article.description}</p>
            </div>

            <div class="card card-style">
                <h3>Categories</h3>
                <ul>
                    <li class="badge bg-primary">Bike</li>
                    <li class="badge bg-primary">Services</li>
                    <li class="badge bg-primary">Brand</li>
                    <li class="badge bg-primary color-red">Popular</li>
                </ul>
            </div>
        </div>

        <div class="col-lg-4 col-md-5 col-12">
            <div class="card card-style">
                <h3>Owner</h3>
                <div class="row">
                    <img src="https://www.sinrumbofijo.com/wp-content/uploads/2016/05/default-placeholder.png"
                         style="max-height: 20px" alt="#">
                    <p>${owner.firstName} ${owner.lastName}</p>
                </div>
            </div>

            <div class="card card-style">
                <h3>Contact Owner</h3>
                <form>
                    <div class="row">
                        <div class="col-12">
                            <input type="text" name="name" class="form-control form-control-custom"
                                   placeholder="Your Name">
                        </div>
                        <div class="col-12">
                            <input type="email" name="email" class="form-control form-control-custom"
                                   placeholder="Your Email">
                        </div>
                        <div class="col-12">
                                <textarea name="#" class="form-control form-control-custom"
                                          placeholder="Your Message">
                                </textarea>
                        </div>
                        <div class="col-12">
                            <div class="button">
                                <button type="submit" class="btn">Send Email</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
<script src="<c:url value="/resources/js/main.js" />" defer></script>
</html>
