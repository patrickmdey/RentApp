import { skipToken } from "@reduxjs/toolkit/dist/query";
import { Badge, Card, Col, Container, Row } from "react-bootstrap";
import { GeoAltFill } from "react-bootstrap-icons";
import { useLocation, useParams } from "react-router-dom";
import Rating from "../components/Rating";
import ReviewList from "../components/ReviewList";
import { useFindArticle } from "../features/api/articles/articlesSlice";
import {
  useFindCategory,
  useListCategoriesFromArticle,
} from "../features/api/categories/categoriesSlice";
import { useFindLocation } from "../features/api/locations/locationsSlice";
import { useListReviews } from "../features/api/reviews/reviewsSlice";
import { Review } from "../features/api/reviews/types";
import { useFindUser } from "../features/api/users/usersSlice";

// TODO: subdivide into components
function Article() {
  const { id } = useParams();
  // if (id == null) return <>Error</>;

  const {
    data: articleData,
    error,
    isSuccess: articleIsSuccess,
  } = useFindArticle(
    new URL("http://localhost/api/articles/".concat(id || "1")) //TODO: ojoo
  );

  const { data: categoriesData, isSuccess: categoriesIsSuccess } =
    useListCategoriesFromArticle(
      articleIsSuccess && articleData ? articleData.categoriesUrl : skipToken
    );

  const { data: reviewsData, isSuccess: reviewsIsSuccess } = useListReviews(
    articleIsSuccess && articleData ? articleData.reviewsUrl : skipToken
  );

  const { data: ownerData, isSuccess: ownerIsSuccess } = useFindUser(
    articleIsSuccess && articleData ? articleData.ownerUrl : skipToken
  );

  const { data: locationData, isSuccess: locationIsSuccess } = useFindLocation(
    ownerIsSuccess && ownerData ? ownerData.locationUrl : skipToken
  );

  return (
    <Container className="min-height">
      {articleIsSuccess && articleData && (
        <>
          <Card className="card-style">
            <Row className="g-0">
              <Col md={4} className="justify-content-center align-items-center">
                <img
                  className="img-thumbnail rounded-start article-img"
                  src="https://www.fabricocina.com/wp-content/uploads/2018/06/image_large.png"
                  alt="articlePicture"
                />
                <div className="d-flex flex-wrap">{}</div>
              </Col>
              <Col md={1} />
              <Col md={7}>
                <p className="text-muted small">1</p>
                <Card.Title as="h2" className="my-n2">
                  {articleData.title}
                </Card.Title>
                <Card.Subtitle
                  className="article-location d-flex"
                  color="color-action"
                >
                  <GeoAltFill size="4%"></GeoAltFill>
                  <p className="lead">
                    {/* {locationData && <a href="/">{locationData.name}</a>} */}
                  </p>
                </Card.Subtitle>
                <div className="d-flex">
                  {categoriesData &&
                    categoriesData.map((category, i) => (
                      <h5 key={i}>
                        <Badge className="m-1 bg-color-grey color-rentapp-black">
                          {category.description}
                        </Badge>
                      </h5>
                    ))}
                </div>
                {reviewsData && reviewsData.length === 0 ? (
                  <p className="lead text-muted">No hay reviews</p>
                ) : (
                  <Rating rating={articleData.rating}></Rating>
                )}
                <h3 className="mt-n1 h3 color-rentapp-red">
                  ${articleData.pricePerDay}
                </h3>
                {/*TODO: aca va el boton para la solicitud */}
              </Col>
            </Row>
          </Card>
          <Row className="w-100 g-0 justify-content-between">
            <Col md={8} className="pe-md-3 pe-0">
              <Card className="card-style article-card">
                <Card.Title as="h3">Descripción</Card.Title>
                <hr></hr>
                <Card.Body className="lead">
                  {articleData.description}
                </Card.Body>
              </Card>
              <Card className="card-style">
                {reviewsData && <ReviewList {...reviewsData}></ReviewList>}
              </Card>
            </Col>
            <Col md={4}>
              <Card className="card-style">
                <Card.Title>Owner</Card.Title>
                <hr></hr>
                <Row className="align-items-center">
                  <div className="avatar-container col-4">
                    <img
                      width="50%"
                      src="https://www.fabricocina.com/wp-content/uploads/2018/06/image_large.png"
                    ></img>
                  </div>
                  <span className="lead col-8">
                    {ownerData && (
                      <span>
                        {ownerData.firstName} {ownerData.lastName}
                      </span>
                    )}
                  </span>
                </Row>
              </Card>
            </Col>
          </Row>
        </>
      )}
    </Container>
  );
}

export default Article;
