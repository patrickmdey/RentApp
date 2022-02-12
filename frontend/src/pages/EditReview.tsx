import { Helmet } from 'react-helmet-async';
import { useFindArticle } from '../features/api/articles/articlesSlice';
import { Container } from 'react-bootstrap';
import ReviewForm from '../components/Review/ReviewForm';
import { strings } from '../i18n/i18n';
import { useFindReview } from '../features/api/reviews/reviewsSlice';
import { skipToken } from '@reduxjs/toolkit/dist/query';
import LoadingComponent from '../components/LoadingComponent';
import ErrorComponent from '../components/Errors/ErrorComponent';
import { useParams } from 'react-router-dom';

function EditReview() {
	const { id } = useParams();

	const {
		data: review,
		isSuccess: reviewIsSuc,
		isLoading: reviewIsLoad,
		error: reviewError
	} = useFindReview(new URL(`reviews/${id}`, process.env.REACT_APP_BASE_URL).toString());

	const {
		data: article,
		isLoading: articleIsLoad,
		error: articleError
	} = useFindArticle(reviewIsSuc && review ? review.articleUrl : skipToken);

	if (reviewIsLoad || articleIsLoad) return <LoadingComponent />;

	const anyError = reviewError || articleError;
	if (anyError && 'status' in anyError)
		return (
			<ErrorComponent
				error={anyError.status}
				message={typeof anyError.data === 'string' ? anyError.data : undefined}
			/>
		);

	return (
		<>
			<Helmet>
				<title>{strings.collection.review.edit}</title>
			</Helmet>
			{reviewIsSuc && review && article && (
				<Container className='mx-auto min-height'>
					<ReviewForm article={article} review={review} />
				</Container>
			)}
		</>
	);
}

export default EditReview;
