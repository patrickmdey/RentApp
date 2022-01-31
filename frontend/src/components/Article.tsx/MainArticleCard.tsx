import { Badge, Button, Card, Col, Form, Modal, Row } from "react-bootstrap";
import { GeoAltFill } from "react-bootstrap-icons";
import { Category } from "../../features/api/categories/types";
import { Article } from "../../features/api/articles/types";
import { Review } from "../../features/api/reviews/types";
import Rating from "../Rating";
import { strings } from "../../i18n/i18n";
import { Location } from "../../features/api/locations/types";
import { useListImages } from "../../features/api/images/imagesSlice";
import RequestForm from "../Requests/RequestForm";
import { useState } from "react";

function setActiveImage(src: string) {
  const htmlImg = document.getElementById("main-img");
  if (htmlImg === null) {
    console.log("NO HICE NA");
    return;
  }
  htmlImg.setAttribute("src", src);
}

function MainArticleCard(props: {
  article: Article;
  categories: Category[] | undefined;
  reviews: Review[] | undefined;
  location: Location | undefined;
}) {
  const { article, categories, reviews, location } = props;
  const { data: articleImages, isSuccess } = useListImages(article.imagesUrl);

  const [show, setShow] = useState(false);

  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  return (
    <>
      <Card className="card-style">
        <Row className="g-0">
          <Col md={4} className="justify-content-center align-items-center">
            {isSuccess && articleImages && articleImages.length > 0 && (
              <div>
                <img
                  className="img-thumbnail rounded-start article-img"
                  src={articleImages[0].url.toString()}
                  alt="articlePicture"
                  id="main-img"
                />
                <div className="d-flex flex-wrap justify-content-center mt-2">
                  {articleImages.map((img, i) => (
                    <button
                      key={i}
                      className="btn-link mx-2"
                      onClick={() => setActiveImage(img.url.toString())}
                    >
                      <img
                        src={img.url.toString()}
                        width="40px"
                        height="40px"
                        alt={"image" + i}
                      ></img>
                    </button>
                  ))}
                </div>
              </div>
            )}
          </Col>
          <Col md={1} />
          <Col md={7}>
            <p className="text-muted lead small">
              {article.timesRented} {strings.collection.article.timesRented}
            </p>
            <Card.Title as="h2" className="my-n2">
              {article.title}
            </Card.Title>
            <Card.Subtitle
              className="article-location d-flex mt-3"
              color="color-action"
            >
              <GeoAltFill size="5%"></GeoAltFill>
              <p className="lead">
                {location && <a href="/">{location.name}</a>}
              </p>
            </Card.Subtitle>
            <div className="d-flex flex-wrap mt-3">
              {categories &&
                categories.map((category, i) => (
                  <h5 key={i}>
                    <Badge className="m-1 bg-color-grey color-rentapp-black">
                      {category.description}
                    </Badge>
                  </h5>
                ))}
            </div>
            {reviews && reviews.length === 0 ? (
              <p className="lead text-muted">
                {strings.collection.review.noReviews}
              </p>
            ) : (
              <Rating
                rating={article.rating}
                timesReviewed={article.timesReviewed}
              ></Rating>
            )}
            <h3 className="mt-n1 fw-bold h3 color-rentapp-red">
              ${article.pricePerDay}
            </h3>
            <Row className="my-3">
              <Button onClick={handleShow}>Rent</Button>
            </Row>
          </Col>
        </Row>
      </Card>
      <Modal show={show} onHide={handleClose}>
        <Modal.Header className="bg-color-grey" closeButton>
          <Modal.Title>
            {strings.collection.article.requestArticle.title}
          </Modal.Title>
        </Modal.Header>
        <RequestForm />
      </Modal>
    </>
  );
}

export default MainArticleCard;
