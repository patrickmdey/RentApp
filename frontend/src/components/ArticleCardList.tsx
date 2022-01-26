import { Row, Col } from "react-bootstrap";
import { Article } from "../features/api/articles/types";
import ArticleCard from "./ArticleCard";

function ArticleCardList(props: {
  articles: Article[];
  maxPage: Number;
  articlesPerRow: Number;
}) {
  const { articles, maxPage, articlesPerRow } = props;
  return (
    <div className="justify-content-center w-100">
      <Row
        md={3}
        lg={3}
        className="w-100 justify-content-start container-height"
      >
        {articles.map((article, index) => (
          <Col>
            <ArticleCard key={index} {...article}></ArticleCard>
          </Col>
        ))}
      </Row>
    </div>
  );
}

export default ArticleCardList;
