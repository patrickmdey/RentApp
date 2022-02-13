import { Helmet } from 'react-helmet-async';
import { useFindArticle } from '../api/articles/articlesSlice';
import { Container } from 'react-bootstrap';
import ReviewForm from '../components/Forms/ReviewForm';
import { strings } from '../i18n/i18n';
import { useSearchParams } from 'react-router-dom';

function CreateReview() {
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

export default CreateReview;
