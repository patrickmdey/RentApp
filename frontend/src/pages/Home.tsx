import { useListArticles } from "../features/api/articles/articlesSlice";
import ArticleCardList from "../components/ArticleCardList";
import FilterCard from "../components/FilterCard";
import { Col, Container, Row } from "react-bootstrap";

function Home() {
  const { data, error, isLoading } = useListArticles({});

  return (
    <Container>
      <Row className="align-items-start justify-content-center">
        <Col md={3} lg={3}>
          <FilterCard onUpdate={(data) => console.log(data)} />
        </Col>
        <Col md={9} lg={9}>
          {error ? (
            <>Oh no, there was an error</>
          ) : isLoading ? (
            <>Loading</>
          ) : (
            data &&
            (data.length === 0 ? (
              <h1>No hay articulos</h1>
            ) : (
              <ArticleCardList
                articles={data}
                maxPage={1} //TODO: Harcoded
                articlesPerRow={3}
              ></ArticleCardList>
            ))
          )}
        </Col>
      </Row>
    </Container>
  );
}

export default Home;
