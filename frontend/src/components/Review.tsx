import { Row } from "react-bootstrap";
import { Review as ReviewT } from "../features/api/reviews/types";
import Rating from "./Rating";

function Review(review: ReviewT) {
  const { rating, message, createdAt } = review;
  return (
    <>
      <Row className="align-items-center my-2">
        <h4 className="col-6 h4">Nombre del que escribio la rev</h4>
        <h5 className="col-5 h5">{createdAt.toString}</h5>
        {/* <c:if test="${userId == review.renter.id}">
        <a class="col-1 fa-lg" href="${editReview}">
          <i class="color-action bi bi-pencil-fill"></i>
        </a>
      </c:if> */}
      </Row>
      <Rating rating={rating} timesReviewed={-1} />
      <p className="text-muted mt-2">{message}</p>
    </>
  );
}

export default Review;
