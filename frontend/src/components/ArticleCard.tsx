import { Card } from "react-bootstrap";
import { Article } from "../features/api/articles/types";
import Rating from "./Rating";

function ArticleCard(article: Article) {
  const { title, pricePerDay, rating, imagesUrl } = article;
  // const { data, error, isSuccess } = useListImages(imagesUrl);
  // console.log(data);
  return (
    <Card className="marketplace-card-style">
      {/* {isSuccess && data && data.length && (
        <Card.Img variant="top" src={data[0].url.toString()} />
      )} */}
      <Card.Title>{title}</Card.Title>
      <Rating rating={rating}></Rating>
      <Card.Text>{pricePerDay}</Card.Text>
    </Card>
  );
}

export default ArticleCard;
