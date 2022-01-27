import { Container, Card, Row, Col, Badge } from "react-bootstrap";
import { GeoAltFill } from "react-bootstrap-icons";
import { useLocation } from "react-router-dom";
import Rating from "../components/Rating";
import { useFindArticle } from "../features/api/articles/articlesSlice";
import ReviewList from "../components/ReviewList";
import { Review } from "/Users/patrick/Desktop/Carpeta/ITBA/PAW/paw-2021b-3/frontend/src/features/api/reviews/types";

function Article() {
  const location = useLocation();

  //   let articleURL = new URL("localhost".concat(location.pathname));

  const isLoading = false;
  const error = false;
  //   const { data, error, isLoading } = useFindArticle(
  //new URL("http://localhost".concat(location.pathname))
  //   );

  var ejUrl = new URL(
    "https://stackoverflow.com/questions/15842239/how-to-cast-a-string-to-an-url-in-java"
  );

  var data: {
    timesRented: Number;
    title: String;
    description: String;
    descriptionTitle: String;
    rating: Number;
    location: String;
    categories: String[];
    pricePerDay: Number;
    reviews: Review[];
  } = {
    timesRented: 1,
    title: "Articulo de prueba",
    description: "Esto es una descripcion",
    descriptionTitle: "Esto es el titulo de la desc descripcion",
    rating: 3,
    location: "Palermo",
    categories: ["categ1", "categ2", "categ3"],
    pricePerDay: 200,
    reviews: [
      {
        rating: 3,
        message: "maso",
        createdAt: new Date(),
        articleUrl: ejUrl,
        renterUrl: ejUrl,
        url: ejUrl,
      },
      {
        rating: 1,
        message: "malo",
        createdAt: new Date(),
        articleUrl: ejUrl,
        renterUrl: ejUrl,
        url: ejUrl,
      },
      {
        rating: 2,
        message: "malo",
        createdAt: new Date(),
        articleUrl: ejUrl,
        renterUrl: ejUrl,
        url: ejUrl,
      },
    ],
  };

  return (
    <Container className="min-height">
      {error ? (
        <p>Error!</p>
      ) : isLoading ? (
        <img
          src="https://media.giphy.com/media/3oEjI6SIIHBdRxXI40/200.gif"
          alt="loading"
        ></img>
      ) : (
        data && (
          <>
            <Card className="card-style">
              <Row className="g-0">
                <Col
                  md={4}
                  className="justify-content-center align-items-center"
                >
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
                    {data.title}
                  </Card.Title>
                  <Card.Subtitle
                    className="article-location d-flex"
                    color="color-action"
                  >
                    <GeoAltFill size="4%"></GeoAltFill>
                    <p className="lead">
                      <a href="/">{data.location}</a>
                    </p>
                  </Card.Subtitle>
                  <div className="d-flex">
                    {data.categories.map((category, i) => (
                      <h5 key={i}>
                        <Badge className="m-1 bg-color-grey color-rentapp-black">
                          {category}
                        </Badge>
                      </h5>
                    ))}
                  </div>
                  {data.reviews.length === 0 ? (
                    <p className="lead text-muted">No hay reviews</p>
                  ) : (
                    <Rating rating={data.rating}></Rating>
                  )}
                  <h3 className="mt-n1 h3 color-rentapp-red">
                    ${data.pricePerDay}
                  </h3>
                  {/*TODO: aca va el boton para la solicitud */}
                </Col>
              </Row>
            </Card>
            <Row className="w-100 g-0 justify-content-between">
              <Col md={8} className="pe-md-3 pe-0">
                <Card className="card-style article-card">
                  <Card.Title as="h3">{data.descriptionTitle}</Card.Title>
                  <hr></hr>
                  <Card.Body className="lead">{data.description}</Card.Body>
                </Card>
                <Card className="card-style">
                  <ReviewList reviews={data.reviews}></ReviewList>
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
                    <span className="lead col-8">Nombre Apellido</span>
                  </Row>
                </Card>
              </Col>
            </Row>
          </>
        )
      )}
    </Container>
  );
}

export default Article;
