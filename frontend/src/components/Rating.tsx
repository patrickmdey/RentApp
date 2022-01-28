import { Star, StarFill } from "react-bootstrap-icons";

function Rating(props: { rating: Number; timesReviewed: Number }) {
  const { rating, timesReviewed } = props;
  return (
    <div>
      {rating > 0 && (
        <div className="d-flex align-items-start mt-2 mb-2">
          <div>
            {[...Array(5)].map((_, idx) => {
              return idx <= rating ? (
                <StarFill className="mr-1" size="7%" color="red" key={idx} />
              ) : (
                <Star
                  className="mr-1"
                  size="7%"
                  color="rentapp-red"
                  key={idx}
                />
              );
            })}
          </div>
          <span>
            {timesReviewed !== null && (
              <span className="small text-muted ms-1">({timesReviewed})</span>
            )}
          </span>
        </div>
      )}
    </div>
  );
}

export default Rating;
