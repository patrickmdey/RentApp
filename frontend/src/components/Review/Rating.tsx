import { Star, StarFill } from 'react-bootstrap-icons';
import { Rating as SimpleStarRating } from 'react-simple-star-rating';
function Rating(props: { rating: number; timesReviewed: number | undefined }) {
	const { rating, timesReviewed } = props;
	return (
		<div>
			{rating > 0 && (
				<div className='d-flex align-items-center mb-2'>
					<SimpleStarRating readonly={true} ratingValue={rating * 20} size={30} fillColor='#ff0000' />
					<span>
						{timesReviewed !== undefined && (
							<span className='small text-muted ms-1'>({timesReviewed})</span>
						)}
					</span>
				</div>
			)}
		</div>
	);
}

export default Rating;
