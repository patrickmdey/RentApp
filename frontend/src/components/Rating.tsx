import { Star, StarFill } from "react-bootstrap-icons";

function Rating(props: { rating: Number }) {
  return (
    <div>
      {props.rating > 0 && (
        <div className="d-flex align-items-start mt-2 mb-2">
          <div>
            {[...Array(5)].map((_, idx) => {
              return idx <= props.rating ? (
                <StarFill size="3vh" color="red" key={idx} />
              ) : (
                <Star size="3vh" color="rentapp-red" key={idx} />
              );
            })}
          </div>
        </div>
      )}
    </div>
  );
}

export default Rating;
