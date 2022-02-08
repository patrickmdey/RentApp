import { Helmet } from "react-helmet";
import { useFindArticle } from "../features/api/articles/articlesSlice";
import { Container } from "react-bootstrap";
import ReviewForm from "../components/Review/ReviewForm";
function Review() {
  //TODO: getSearchParams() pero ver como es con router .v6

  const { data: article, isSuccess } = useFindArticle(
    new URL("http://localhost:8080/articles/63")
  );

  return (
    <>
      <Helmet>
        <title>Create Review</title>
      </Helmet>
      {isSuccess && article && (
        <Container style={{ margin: 20 % 0 }} className="mx-auto min-height">
          <ReviewForm article={article} />
        </Container>
      )}
    </>
  );
}

export default Review;
