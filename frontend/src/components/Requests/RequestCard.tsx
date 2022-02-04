import { RentProposal } from "../../features/api/rentProposals/types";
import { Button, Card, Image, Row } from "react-bootstrap";
import { LinkContainer } from "react-router-bootstrap";
import { strings } from "../../i18n/i18n";
import { useFindArticle } from "../../features/api/articles/articlesSlice";
import { useFindUser } from "../../features/api/users/usersSlice";

export default function RequestCard(props: RentProposal) {
  const {
    message,
    startDate,
    endDate,
    seen,
    state,
    url,
    articleUrl,
    renterUrl,
    id,
  } = props;

  const { data: article, isSuccess: articleSuccess } =
    useFindArticle(articleUrl);

  const { data: renter, isSuccess: renterSuccess } = useFindUser(renterUrl);

  return (
    <>
      {articleSuccess && article && renterSuccess && renter && (
        <Card className="card-style my-requests-card my-2">
          {/*// TODO: Duda, esto va a funcionar con el routing que tenemos?*/}
          <LinkContainer
            to={new URL(
              `/articles/${id}`,
              process.env.REACT_APP_BASE_URL
            ).toString()}
          >
            <h3 className="color-action mb-0"> {article.title} </h3>
          </LinkContainer>
          <hr />
          <Row>
            <div className="col-3">
              <Image src={article.imagesUrl.toString()} width="200px" />
            </div>
            <div className="col-9">
              <p className="lead mb-2">
                {strings.formatString(
                  strings.collection.requestCard.name,
                  renter.firstName,
                  renter.lastName
                )}
              </p>
              <Row className="justify-content-start">
                <p className="lead col-lg-6 col-md-6 col-12">
                  {strings.formatString(
                    strings.collection.requestCard.startDate,
                    startDate
                  )}
                </p>
                <p className="lead col-lg-6 col-md-6 col-12">
                  {strings.formatString(
                    strings.collection.requestCard.endDate,
                    endDate
                  )}
                </p>
              </Row>
              <h4> {strings.collection.requestCard.messageTitle}</h4>
              <p> {message} </p>
            </div>
          </Row>
          {state === 0 && (
            <div className="d-flex justify-content-end my-2">
              <Button className="bg-color-action color-grey me-1">
                {strings.collection.requestCard.acceptButton}
              </Button>
              <button className="btn btn-link color-danger">
                {strings.collection.requestCard.rejectButton}
              </button>
            </div>
          )}
        </Card>
      )}
    </>
  );
}
