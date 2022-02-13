import { Card, Container } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { Article } from '../features/api/articles/types';
import { useListImages } from '../features/api/images/imagesSlice';
import { useFindLocation } from '../features/api/locations/locationsSlice';
import { useFindUser } from '../features/api/users/usersSlice';
import Rating from './Rating';
import { skipToken } from '@reduxjs/toolkit/dist/query';
import { GeoAltFill } from 'react-bootstrap-icons';

function ArticleCard(article: Article) {
	const { title, pricePerDay, rating, imagesUrl, ownerUrl, timesReviewed, id } = article;

	const { data: owner, isSuccess: ownerIsSuccess } = useFindUser(ownerUrl);

	const { data: location } = useFindLocation(ownerIsSuccess && owner ? owner.locationUrl : skipToken);

	let navigate = useNavigate();
	const goToArticle = () => {
		let path = new URL(`/articles/${id}`, process.env.REACT_APP_BASE_URL);
		navigate(path);
	};

	const { data, isSuccess } = useListImages(imagesUrl);
	return (
		<Card onClick={goToArticle} className='marketplace-card-style text-dark bg-light mb-4'>
			{isSuccess && data && data.length && (
				<div className='marketplace-card-img-container'>
					<Card.Img className='card-image' variant='top' src={data[0].url.toString()} />
				</div>
			)}

			<Card.Body className='marketplace-card-info-container'>
				<h4 className='text-truncate'>{title}</h4>
				<div className='d-flex mt-1 d-inline-block'>
					<GeoAltFill className='color-primary' size='20px'></GeoAltFill>
					{location && <p className='lead fw-bold color-primary'>{location.name}</p>}
				</div>

				<div>
					<Rating rating={rating} timesReviewed={timesReviewed}></Rating>
				</div>
				<h3>${pricePerDay}</h3>
			</Card.Body>
		</Card>
	);
}

export default ArticleCard;
