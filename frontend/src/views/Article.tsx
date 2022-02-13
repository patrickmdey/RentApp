import { skipToken } from '@reduxjs/toolkit/dist/query';
import { Button, Card, Col, Container, Row } from 'react-bootstrap';
import { useParams } from 'react-router-dom';
import MainArticleCard from '../components/ArticleView/MainArticleCard';
import ReviewList from '../components/Review/ReviewList';
import { useFindArticle, useListRelatedArticles } from '../api/articles/articlesSlice';
import { useListCategoriesFromArticle } from '../api/categories/categoriesSlice';
import { useFindLocation } from '../api/locations/locationsSlice';
import { useListReviews } from '../api/reviews/reviewsSlice';
import { useFindUser } from '../api/users/usersSlice';
import ArticleDescriptionCard from '../components/ArticleView/ArticleDescriptionCard';
import OwnerCard from '../components/ArticleView/OwnerCard';
import { strings } from '../i18n/i18n';
import { Helmet } from 'react-helmet-async';
import { useListRentProposals } from '../api/rentProposals/rentProposalsSlice';
import { SENT_STRING, states } from './Requests';
import { useEffect, useState } from 'react';
import useUserId from '../hooks/useUserId';
import usePaginatedResponse from '../hooks/usePaginatedResponse';
import LoadingComponent from '../components/LoadingComponent';
import Error from '../components/Error';
import { useNavigate } from 'react-router';
import ArticleCardList from '../components/Article/ArticleCardList';

// TODO: subdivide into components
function Article() {
	const { id } = useParams();
	const loggedUserId = useUserId();
	// if (id == null) return <>Error</>;
	const navigate = useNavigate();

	const {
		data: article,
		isSuccess: articleIsSuccess,
		isLoading: articleLoad,
		error: articleError
	} = useFindArticle(new URL(`articles/${id}`, process.env.REACT_APP_BASE_URL).toString());

	const {
		data: related,
		isSuccess: relatedIsSuccess,
		isLoading: relatedLoad,
		error: relatedError
	} = useListRelatedArticles(id ? id : skipToken);

	const {
		data: categories,
		isLoading: categoriesLoad,
		error: categoriesError
	} = useListCategoriesFromArticle(articleIsSuccess && article ? article.categoriesUrl : skipToken);

	const {
		data: reviews,
		isLoading: reviewsLoad,
		error: reviewError
	} = useListReviews(articleIsSuccess && article ? article.reviewsUrl : skipToken);

	const {
		data: owner,
		isSuccess: ownerIsSuccess,
		isLoading: ownerLoad,
		error: ownerError
	} = useFindUser(articleIsSuccess && article ? article.ownerUrl : skipToken);

	const {
		data: location,
		isLoading: locationLoad,
		error: locationError
	} = useFindLocation(ownerIsSuccess && owner ? owner.locationUrl : skipToken);

	const {
		data: aProp,
		isSuccess: aPropSuccess,
		isLoading: aPropLoad,
		error: aPropError
	} = usePaginatedResponse(
		useListRentProposals(
			loggedUserId !== null
				? {
						userId: loggedUserId,
						type: SENT_STRING,
						state: states.accepted,
						page: 1
				  }
				: skipToken
		)
	);

	const [loggedUserUrl, setLoggedUserUrl] = useState('');
	useEffect(
		() => setLoggedUserUrl(new URL(`users/${loggedUserId}`, process.env.REACT_APP_BASE_URL).toString()),
		[loggedUserId]
	);

	const [hasRented, setHasRented] = useState(false);

	useEffect(() => {
		let acceptedRentProposal =
			aPropSuccess &&
			aProp &&
			article &&
			aProp.find(
				(proposal) => Date.parse(proposal.startDate) < Date.now() && proposal.articleUrl === article.url
			);
		setHasRented(acceptedRentProposal !== null && acceptedRentProposal !== undefined);
	}, [aPropSuccess, aProp, article]);

	const [hasReviewed, setHasReviewed] = useState(false);
	useEffect(
		() =>
			setHasReviewed(
				reviews && article
					? reviews.find(
							(review) =>
								review.renterUrl.toString() === loggedUserUrl && review.articleUrl === article.url
					  ) !== null
					: false
			),
		[reviews, article, loggedUserUrl]
	);

	const [isOwner, setIsOwner] = useState(false);
	useEffect(
		() => setIsOwner(ownerIsSuccess && owner !== undefined ? owner.id === loggedUserId : false),
		[owner, ownerIsSuccess, loggedUserId]
	);

	if (articleLoad || categoriesLoad || reviewsLoad || ownerLoad || locationLoad || aPropLoad)
		return <LoadingComponent />;

	const anyError =
		articleError || relatedError || categoriesError || reviewError || ownerError || locationError || aPropError;
	if (anyError && 'status' in anyError)
		return (
			<Error
				error={anyError.status}
				message={typeof anyError.data === 'string' ? anyError.data : undefined}
			/>
		);
	return (
		<>
			{articleIsSuccess && article && ownerIsSuccess && owner && (
				<>
					<Helmet>
						<title>{article.title}</title>
					</Helmet>
					<Container className='min-height'>
						<MainArticleCard
							article={article}
							categories={categories}
							reviews={reviews}
							location={location}
							isOwner={isOwner}
						/>
						<Row className='w-100 g-0 justify-content-between'>
							<Col md={8} className='pe-md-3 pe-0'>
								<ArticleDescriptionCard articleDescription={article.description} />
							</Col>
							<Col md={4}>
								<OwnerCard owner={owner} />
							</Col>
						</Row>
						<Row className='w-100 g-0 justify-content-between'>
							<Card className='card-style'>
								<div className='d-flex align-items-center justify-content-between'>
									<Card.Title as='h3'>{strings.collection.review.reviews}</Card.Title>
									{hasRented && !hasReviewed && (
										<Button onClick={() => navigate(`/createReview?forArticle=${article.id}`)}>
											{strings.collection.article.createReview}
										</Button>
									)}
								</div>
								<hr></hr>
								{reviews ? (
									<ReviewList reviews={reviews} />
								) : (
									<div className='d-flex justify-content-center my-auto'>
										<p className='lead my-auto'>{strings.collection.noData.noReviews}</p>
									</div>
								)}
							</Card>
						</Row>
						{relatedIsSuccess && related && (
							<Card className='card-style'>
								<Card.Title as='h3'>{strings.collection.article.relatedTitle}</Card.Title>
								<hr />
								<div className='d-flex justify-content-center align-items-center'>
									<ArticleCardList articles={related} articlesPerRow={4} />
								</div>
							</Card>
						)}
					</Container>
				</>
			)}
		</>
	);
}

export default Article;
