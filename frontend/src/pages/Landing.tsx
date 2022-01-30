import CategoriesList from '../components/CategoriesList';
import { Container, Row } from 'react-bootstrap';
import LandingTitle from '../components/LandingTitle';
import ArticleCardList from '../components/ArticleCardList';
import { useListArticles } from '../features/api/articles/articlesSlice';

export default function Landing() {
	// TODO: paremetrize listArticles method, add limit param
	const { data: bestRated, isSuccess: ratedIsSuccess } = useListArticles({
		url: new URL('articles?orderBy=5', process.env.REACT_APP_BASE_URL)
	});
	const { data: mostRented, isSuccess: rentedIsSuccess } = useListArticles({
		url: new URL('articles?orderBy=7', process.env.REACT_APP_BASE_URL)
	});
	return (
		<Container className='d-flex flex-column align-items-center'>
			<div style={{ width: '100%' }} className='row g-0'>
				<LandingTitle />
			</div>
			<div className='py-2'>
				<h1>Top rated articles</h1>
				{ratedIsSuccess && bestRated && (
					<ArticleCardList articlesPerRow={4} maxPage={1} articles={bestRated.slice(0, 4)} />
				)}
			</div>
			<div className='py-2'>
				<CategoriesList />
			</div>
			<div className='py-2'>
				<h1>Most rented articles</h1>
				{rentedIsSuccess && mostRented && (
					<ArticleCardList articlesPerRow={4} maxPage={1} articles={mostRented.slice(0, 4)} />
				)}
			</div>
		</Container>
	);
}
