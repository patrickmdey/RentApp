import { Badge, Card, Col, Row } from "react-bootstrap";
import { GeoAltFill } from "react-bootstrap-icons";
import { Category } from "../../features/api/categories/types";
import { Article } from "../../features/api/articles/types";
import { Review } from "../../features/api/reviews/types";
import Rating from "../Rating";
import { strings } from "../../i18n/i18n";
import { Location } from "../../features/api/locations/types";

function MainArticleCard(props: {
  article: Article;
  categories: Category[] | undefined;
  reviews: Review[] | undefined;
  location: Location | undefined;
}) {
  const { article, categories, reviews, location } = props;
  return (
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
            {article.title}
          </Card.Title>
          <Card.Subtitle
            className="article-location d-flex"
            color="color-action"
          >
            <GeoAltFill size="4%"></GeoAltFill>
            <p className="lead">
              {location && <a href="/">{location.name}</a>}
            </p>
          </Card.Subtitle>
          <div className="d-flex">
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
          <h3 className="mt-n1 h3 color-rentapp-red">${article.pricePerDay}</h3>
          {/*TODO: aca va el boton para la solicitud */}
        </Col>
      </Row>
    </Card>
  );
}

export default MainArticleCard;
