import { skipToken } from '@reduxjs/toolkit/dist/query';
import { Card, Col, Container, Row } from 'react-bootstrap';
import { useParams } from 'react-router-dom';
import MainArticleCard from '../components/Article/MainArticleCard';
import ReviewList from '../components/ReviewList';
import { useFindArticle } from '../features/api/articles/articlesSlice';
import { useListCategoriesFromArticle } from '../features/api/categories/categoriesSlice';
import { useFindLocation } from '../features/api/locations/locationsSlice';
import { useListReviews } from '../features/api/reviews/reviewsSlice';
import { useFindUser } from '../features/api/users/usersSlice';
import ArticleDescriptionCard from '../components/Article/ArticleDescriptionCard';
import OwnerCard from '../components/Article/OwnerCard';
import { strings } from '../i18n/i18n';

// TODO: subdivide into components
function Article() {
	const { id } = useParams();
	// if (id == null) return <>Error</>;

	const { data: articleData, isSuccess: articleIsSuccess } = useFindArticle(
		new URL(`articles/${id}`, process.env.REACT_APP_BASE_URL) //TODO: ojoo
	);

	const { data: categoriesData } = useListCategoriesFromArticle(
		articleIsSuccess && articleData ? articleData.categoriesUrl : skipToken
	);

	const { data: reviewsData } = useListReviews(articleIsSuccess && articleData ? articleData.reviewsUrl : skipToken);

	const { data: ownerData, isSuccess: ownerIsSuccess } = useFindUser(
		articleIsSuccess && articleData ? articleData.ownerUrl : skipToken
	);

	const { data: locationData } = useFindLocation(ownerIsSuccess && ownerData ? ownerData.locationUrl : skipToken);

	console.log(reviewsData);

	return (
		<Container className='min-height'>
			{articleIsSuccess && articleData && (
				<>
					<MainArticleCard
						article={articleData}
						categories={categoriesData}
						reviews={reviewsData}
						location={locationData}
					/>
					<Row className='w-100 g-0 justify-content-between'>
						<Col md={8} className='pe-md-3 pe-0'>
							<ArticleDescriptionCard articleDescription={articleData.description} />
							<Card className='card-style'>
								<Card.Title as='h3'>{strings.collection.review.reviews}</Card.Title>
								<hr></hr>
								{reviewsData && <ReviewList reviews={reviewsData}></ReviewList>}
							</Card>
						</Col>
						<Col md={4}>
							<OwnerCard owner={ownerData} />
						</Col>
					</Row>
				</>
			)}
		</Container>
	);
}

export default Article;
