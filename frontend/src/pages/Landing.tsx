import CategoriesList from '../components/CategoriesList';
import { Container, Row } from 'react-bootstrap';
import LandingTitle from '../components/LandingTitle';
import ArticleCardList from '../components/ArticleCardList';
import { useListArticles } from '../features/api/articles/articlesSlice';
import { Helmet } from 'react-helmet-async';
import { strings } from '../i18n/i18n';

export default function Landing() {
	// TODO: paremetrize listArticles method, add limit param
	const { data: bestRated, isSuccess: ratedIsSuccess } = useListArticles({
		orderBy: 'HIGHER_RATING'
	});
	const { data: mostRented, isSuccess: rentedIsSuccess } = useListArticles({
		orderBy: 'HIGHER_TIMES_RENTED'
	});
	return (
		<>
			<Helmet>
				<title>{strings.collection.pageTitles.marketplace}</title>
			</Helmet>

			<Container className='d-flex flex-column align-items-center'>
				<div style={{ width: '100%' }} className='row g-0'>
					<LandingTitle />
				</div>
				<div className='py-2'>
					<h1>Top rated articles</h1>
					{ratedIsSuccess && bestRated && (
						<ArticleCardList articlesPerRow={4} articles={bestRated.data.slice(0, 4)} />
					)}
				</div>
				<div className='py-2'>
					<CategoriesList />
				</div>
				<div className='py-2'>
					<h1>Most rented articles</h1>
					{rentedIsSuccess && mostRented && (
						<ArticleCardList articlesPerRow={4} articles={mostRented.data.slice(0, 4)} />
					)}
				</div>
			</Container>
		</>
	);
}
