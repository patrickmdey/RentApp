import { Helmet } from 'react-helmet-async';
import { useFindArticle } from '../features/api/articles/articlesSlice';
import { Container } from 'react-bootstrap';
import ReviewForm from '../components/Review/ReviewForm';
import { strings } from '../i18n/i18n';
import { useSearchParams } from 'react-router-dom';

function Review() {
	//TODO: getSearchParams() pero ver como es con router .v6
	const [articleId, setArticleId] = useSearchParams();

	const { data: article, isSuccess } = useFindArticle(
		new URL(`articles/${articleId.get('forArticle')}`, process.env.REACT_APP_BASE_URL).toString()
	);

	return (
		<>
			<Helmet>
				<title>{strings.collection.review.create}</title>
			</Helmet>
			{isSuccess && article && (
				<Container style={{ width: '50%' }} className='min-height'>
					<ReviewForm article={article} />
				</Container>
			)}
		</>
	);
}

export default Review;
