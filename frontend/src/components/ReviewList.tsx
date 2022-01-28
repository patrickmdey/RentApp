import { Row } from "react-bootstrap";
import { Review as ReviewT } from "../features/api/reviews/types";
import Review from "./Review";

function ReviewList(reviews: ReviewT[]) {
  // const { reviews } = props;
  console.log(reviews);
  return (
    <div>
      <Row>
        <h3 className="col-8 h3">Reviews</h3>

        {/* TODO: Can review
         <c:if test="${canReview}">
          <div className="col-4">
            <control:linkButton
              href="${writeReview}"
              labelCode="article.createReview.title"
              color="bg-color-action color-grey"
            /> 
          </div>
        </c:if>
        */}
      </Row>
      <hr></hr>
      {reviews.length === 0 ? (
        <p className="lead">No hay reviews</p>
      ) : (
        <div>
          {reviews.map((review, i) => (
            <div>
              <Review key={i} {...review}></Review>
              <hr />
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

export default ReviewList;
