import { Helmet } from 'react-helmet-async';
import { useFindArticle } from '../features/api/articles/articlesSlice';
import { Container } from 'react-bootstrap';
import ReviewForm from '../components/Review/ReviewForm';
import { strings } from '../i18n/i18n';

function Review() {
	//TODO: getSearchParams() pero ver como es con router .v6

	const { data: article, isSuccess } = useFindArticle(
		new URL('articles/74', process.env.REACT_APP_BASE_URL).toString()
	);

	return (
		<>
			<Helmet>
				<title>{strings.collection.review.create}</title>
			</Helmet>
			{isSuccess && article && (
				<Container className='mx-auto min-height'>
					<ReviewForm article={article} />
				</Container>
			)}
		</>
	);
}

export default Review;
