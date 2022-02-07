import { skipToken } from "@reduxjs/toolkit/dist/query";
import { Button, Card, Col, Container, Row } from "react-bootstrap";
import { useParams } from "react-router-dom";
import MainArticleCard from "../components/Article/MainArticleCard";
import ReviewList from "../components/ReviewList";
import { useFindArticle } from "../features/api/articles/articlesSlice";
import { useListCategoriesFromArticle } from "../features/api/categories/categoriesSlice";
import { useFindLocation } from "../features/api/locations/locationsSlice";
import { useListReviews } from "../features/api/reviews/reviewsSlice";
import { useFindUser } from "../features/api/users/usersSlice";
import ArticleDescriptionCard from "../components/Article/ArticleDescriptionCard";
import OwnerCard from "../components/Article/OwnerCard";
import { strings } from "../i18n/i18n";
import { Helmet } from "react-helmet";
import {
  useFindRentProposal,
  useListRentProposals,
} from "../features/api/rentProposals/rentProposalsSlice";
import { SENT_STRING, states } from "./Request";
import { useEffect, useState } from "react";
import useUserId from "../hooks/useUserId";

// TODO: subdivide into components
function Article() {
  const { id } = useParams();
  const loggedUserId: number = useUserId();
  // if (id == null) return <>Error</>;

  const { data: articleData, isSuccess: articleIsSuccess } = useFindArticle(
    new URL(`articles/${id}`, process.env.REACT_APP_BASE_URL) //TODO: ojoo
  );

  const { data: categoriesData } = useListCategoriesFromArticle(
    articleIsSuccess && articleData ? articleData.categoriesUrl : skipToken
  );

  const { data: reviewsData } = useListReviews(
    articleIsSuccess && articleData ? articleData.reviewsUrl : skipToken
  );

  const { data: ownerData, isSuccess: ownerIsSuccess } = useFindUser(
    articleIsSuccess && articleData ? articleData.ownerUrl : skipToken
  );

  const { data: locationData } = useFindLocation(
    ownerIsSuccess && ownerData ? ownerData.locationUrl : skipToken
  );

  console.log("PASE POR ACA");

  const { data: aProp, isSuccess: aPropSuccess } = useListRentProposals({
    userId: loggedUserId,
    type: SENT_STRING,
    state: states.accepted,
  });

  const [loggedUserUrl, setLoggedUserUrl] = useState("");
  useEffect(
    () =>
      setLoggedUserUrl(
        new URL(
          `users/${loggedUserId}`,
          process.env.REACT_APP_BASE_URL
        ).toString()
      ),
    [loggedUserId]
  );

  const [hasRented, setHasRented] = useState(false);
  useEffect(
    () =>
      setHasRented(
        aPropSuccess && aProp && articleData
          ? aProp.find(
              (proposal) =>
                Date.parse(proposal.startDate) < Date.now() &&
                proposal.articleUrl === articleData.url
            ) !== null
          : false
      ),
    [aPropSuccess, aProp, articleData]
  );

  const [hasReviewed, setHasReviewed] = useState(false);
  useEffect(
    () =>
      setHasReviewed(
        reviewsData && articleData
          ? reviewsData.find(
              (review) =>
                review.renterUrl.toString() === loggedUserUrl &&
                review.articleUrl === articleData.url
            ) !== null
          : false
      ),
    [reviewsData, articleData, loggedUserUrl]
  );

  return (
    <>
      {articleIsSuccess && articleData && (
        <>
          <Helmet>
            <title>{articleData.title}</title>
          </Helmet>
          <Container className="min-height">
            <h3>{loggedUserUrl}</h3>
            <h3 color="orange">hasRented: {hasRented.toString()}</h3>
            <h3 color="blue">hasReviewed: {hasReviewed.toString()}</h3>

            <MainArticleCard
              article={articleData}
              categories={categoriesData}
              reviews={reviewsData}
              location={locationData}
            />
            <Row className="w-100 g-0 justify-content-between">
              <Col md={8} className="pe-md-3 pe-0">
                <ArticleDescriptionCard
                  articleDescription={articleData.description}
                />

                <Card className="card-style">
                  <div className="d-flex align-items-center justify-content-between">
                    <Card.Title as="h3">
                      {strings.collection.review.reviews}
                    </Card.Title>
                    {hasRented && !hasReviewed && (
                      <Button>{strings.collection.article.rent}</Button>
                    )}
                  </div>
                  <hr></hr>
                  {reviewsData ? (
                    <ReviewList reviews={reviewsData}></ReviewList>
                  ) : (
                    <div className="d-flex justify-content-center my-auto">
                      <p className="lead my-auto">No hay reseñas</p>
                    </div>
                  )}
                </Card>
              </Col>
              <Col md={4}>
                <OwnerCard owner={ownerData} />
              </Col>
            </Row>
          </Container>
        </>
      )}
    </>
  );
}

export default Article;
