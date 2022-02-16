import { useListArticles } from '../api/articles/articlesSlice';
import { ListArticleParameters } from '../api/articles/types';
import ArticleCardList from '../components/Article/ArticleCardList';
import FilterCard from '../components/Marketplace/FilterCard';
import { Col, Container, Row } from 'react-bootstrap';
import usePaginatedResponse from '../hooks/usePaginatedResponse';
import PagesList from '../components/PagesList';
import { useState } from 'react';
import { Helmet } from 'react-helmet-async';
import { strings } from '../i18n/i18n';
import NoDataCard from '../components/NoDataCard';
import LoadingComponent from '../components/LoadingComponent';
import Error from '../components/Error';
import { URLSearchParamsInit, useSearchParams } from 'react-router-dom';
import { useEffect } from 'react';
import Filters from '../components/Marketplace/Filters';

function serialize<T>(data: T): URLSearchParamsInit {
	return Object.entries(data);
}

function deserialize<T extends Object>(searchParams: URLSearchParams): T {
	return Array.from(searchParams.entries()).reduce(
		(acc, el) => ({
			[el[0]]: el[1],
			...acc
		}),
		{} as T
	);
}

function processFilters(filters: ListArticleParameters) {
	let newFilters = { ...filters };
	if (isNaN(filters.initPrice || NaN)) delete newFilters.initPrice;
	if (isNaN(filters.endPrice || NaN)) delete newFilters.endPrice;
	return newFilters;
}

function useProcessedFilters(unprocessed: ListArticleParameters) {
	const [filters, setFilters] = useState(processFilters(unprocessed));
	useEffect(() => {
		setFilters(processFilters(unprocessed));
	}, [unprocessed]);
	return filters;
}

function Marketplace() {
	const [searchParams, setSearchParams] = useSearchParams();

	const [page, setPage] = useState(1);
	const [filters, setFilters] = useState<ListArticleParameters>(deserialize(searchParams));

	const processedFilters = useProcessedFilters(filters);

	useEffect(() => {
		setSearchParams(serialize(processedFilters));
	}, [filters]);

	function removeSearchParam(name: keyof ListArticleParameters) {
		setFilters((prev) => {
			let copy = { ...prev };
			delete copy[name];
			return copy;
		});
	}

	const { data, pages, error, isLoading } = usePaginatedResponse(
		useListArticles({
			page: page,
			...processedFilters
		})
	);

	const anyError = error;
	if (anyError && 'originalStatus' in anyError)
		return <Error error={anyError.originalStatus} message={anyError.data} />;

	return (
		<>
			<Helmet>
				<title>{strings.collection.pageTitles.marketplace}</title>
			</Helmet>
			<Container>
				<Row className='align-items-start justify-content-center'>
					<Col md={3} lg={3}>
						<FilterCard
							defaultValues={deserialize(searchParams)}
							onClear={() => setFilters({})}
							onSubmit={(data) => setFilters(data)}
						/>
					</Col>
					<Col md={9} lg={9}>
						<Filters filters={filters} removeSearchParam={removeSearchParam} />
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
