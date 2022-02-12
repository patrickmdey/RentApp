import { Row } from 'react-bootstrap';
import { Review as ReviewT } from '../features/api/reviews/types';
import { strings } from '../i18n/i18n';
import Review from './Review';

function ReviewList(props: { reviews: ReviewT[] }) {
	const { reviews } = props;
	return (
		<div>
			{reviews.length === 0 ? (
				<p className='lead'>{strings.collection.noData.noReviews}</p>
			) : (
				<div>
					{reviews.map((review, i) => (
						<div>
							<Review key={i} {...review}></Review>
							<hr />
						</div>
					))}
				</div>
			)}
		</div>
	);
}

export default ReviewList;
