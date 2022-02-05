import { Star, StarFill } from "react-bootstrap-icons";

function Rating(props: { rating: Number; timesReviewed: Number }) {
  const { rating, timesReviewed } = props;
  return (
    <div>
      {rating > 0 && (
        <div className="d-flex align-items-start mb-2">
          <div>
            {[...Array(5)].map((_, idx) => {
              return idx <= rating ? (
                <StarFill
                  className="mr-1 color-rentapp-red"
                  size="20px"
                  key={idx}
                />
              ) : (
                <Star className="mr-1" size="20px" key={idx} />
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
