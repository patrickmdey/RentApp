import { useListArticles } from "../api/articles/articlesSlice";
import { ListArticleParameters } from "../api/articles/types";
import ArticleCardList from "../components/Article/ArticleCardList";
import FilterCard from "../components/Marketplace/FilterCard";
import { Col, Container, Row } from "react-bootstrap";
import usePaginatedResponse from "../hooks/usePaginatedResponse";
import PagesList from "../components/PagesList";
import { useState } from "react";
import { Helmet } from "react-helmet-async";
import { strings } from "../i18n/i18n";
import NoDataCard from "../components/NoDataCard";
import LoadingComponent from "../components/LoadingComponent";
import Error from "../components/Error";

import {
  createSearchParams,
  URLSearchParamsInit,
  useSearchParams,
} from "react-router-dom";
import { useEffect } from "react";

function serialize<T>(data: T): URLSearchParamsInit {
  return Object.entries(data);
}

function deserialize<T extends Object>(searchParams: URLSearchParams): T {
  return Array.from(searchParams.entries()).reduce(
    (acc, el) => ({
      [el[0]]: el[1],
      ...acc,
    }),
    {} as T
  );
}

function Marketplace() {
  const [searchParams, setSearchParams] = useSearchParams();

  const [page, setPage] = useState(1);
  const [filters, setFilters] = useState<ListArticleParameters>({});

  useEffect(() => {
    setSearchParams(serialize(filters));
  }, [filters]);

  const { data, pages, error, isLoading } = usePaginatedResponse(
    useListArticles({
      page: page,
      ...filters,
    })
  );

  if (error && "originalStatus" in error)
    return <Error error={error.originalStatus} message={error.data} />;

  return (
    <>
      <Helmet>
        <title>{strings.collection.pageTitles.marketplace}</title>
      </Helmet>
      <Container>
        <Row className="align-items-start justify-content-center">
          <Col md={3} lg={3}>
            <FilterCard
              defaultValues={deserialize(searchParams)}
              onClear={() => setFilters({})}
              onSubmit={(data) => setFilters(data)}
            />
          </Col>
          <Col md={9} lg={9}>

            {/*EMPIEZAN LOS FILTROS*/}

            {filters.name && filters.name.length > 0 && (
              <div className="d-flex align-items-center">
                <span className="me-2 align-middle">
                  {strings.collection.filterInfo.search + filters.name}
                </span>
              </div>
            )}

            {(filters.category || filters.user) && (
              <div className="d-flex align-items-center">
                <p>{strings.collection.filterInfo.filtering}</p>

                {filters.category && ( //TODO agregar una cruz para sacar cada uno
                  <div className="badge bg-color-secondary mx-2 p-2">
                    <i className="bi bi-tag"></i>
                    <p>{filters.category}</p>
                    <a href="${removeCategoryUrl}" className="text-light">
                      X
                    </a>
                  </div>
                )}

                {filters.user && (
                  <div className="badge bg-color-secondary mx-2 p-2">
                    <i className="fa fa-user"></i>
                    <p>{filters.user}</p>
                    <a href="${removeUserUrl}" className="text-light">
                      X
                    </a>
                  </div>
                )}
              </div>
            )}

            {filters.location && (
              <div className="d-flex align-items-center">
                <span className="me-2 align-middle">
                  {filters.location}
                  <i className="bi bi-pin-map"></i>
                </span>
              </div>
            )}

            {/*TERMINAN LOS FILTROS*/}

            {isLoading ? (
              <LoadingComponent />
            ) : data === null || (data && data.length === 0) ? (
              <NoDataCard
                title={strings.collection.noData.articleNotFoundTitle}
                subtitle={strings.collection.noData.articleNotFoundSubtitle}
              />
            ) : (
              data && (
                <>
                  <ArticleCardList articles={data} articlesPerRow={3} />
                  <PagesList pages={pages} page={page} setPage={setPage} />
                </>
              )
            )}
          </Col>
        </Row>
      </Container>
    </>
  );
}

export default Marketplace;
