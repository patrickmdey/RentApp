import { Row } from 'react-bootstrap';
import { Review as ReviewT } from '../../api/reviews/types';
import { strings } from '../../i18n/i18n';
import ReviewCard from './ReviewCard';

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
							<ReviewCard key={i} review={review}/>
							<hr />
						</div>
					))}
				</div>
			)}
		</div>
	);
}

export default ReviewList;
