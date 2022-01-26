import { Card } from "react-bootstrap";
import { GeoAlt, GeoAltFill } from "react-bootstrap-icons";
import { Article } from "../features/api/articles/types";
import { useFindLocation } from "../features/api/locations/locationsSlice";
import { useFindUser } from "../features/api/users/usersSlice";
import Rating from "./Rating";
import ArticleCardLocation from "./ArticleCardLocation";

function ArticleCard(article: Article) {
  const { title, pricePerDay, rating, imagesUrl, ownerUrl } = article;

  const { data: owner } = useFindUser(ownerUrl);

  // const { data, error, isSuccess } = useListImages(imagesUrl);
  // console.log(data);
  return (
    <Card className="marketplace-card-style text-dark bg-light mb-4">
      {/* {isSuccess && data && data.length && (
        <Card.Img variant="top" src={data[0].url.toString()} />
      )} */}
      <h1>{owner && owner.firstName}</h1>
      <div className="marketplace-card-info-container">
        <Card.Title as="h3">{title}</Card.Title>
        <Card.Subtitle>
          {/* {owner && <ArticleCardLocation locationUrl={owner?.locationUrl} />} */}
          <Rating rating={rating}></Rating>
          <p>${pricePerDay}</p>
        </Card.Subtitle>
      </div>
    </Card>
  );
}

export default ArticleCard;
