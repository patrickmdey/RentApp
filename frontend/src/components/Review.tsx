import { Row } from 'react-bootstrap';
import { PencilFill } from 'react-bootstrap-icons';
import { useNavigate } from 'react-router-dom';
import { Review as ReviewT } from '../features/api/reviews/types';
import { useFindUser } from '../features/api/users/usersSlice';
import useUserId from '../hooks/useUserId';
import Rating from './Rating';

function Review(props: { review: ReviewT; timesReviewed?: number }) {
	const { rating, message, createdAt, renterUrl, id } = props.review;

	const { data: reviewer, isSuccess } = useFindUser(renterUrl);

	const loggedId = useUserId();

	let navigate = useNavigate();
	const goToEditReview = () => {
		let path = new URL(`/editReview/${id}`, process.env.REACT_APP_BASE_URL);
		navigate(path); //TODO: checkear que creo que el tema de authorize en front no esta funcando para esto
	};

	return (
		<>
			{isSuccess && reviewer && (
				<div>
					<Row className='align-items-center my-2'>
						<h4 className='col-6 h4'>
							{reviewer.firstName} {reviewer.lastName}
						</h4>
						<h5 className='col-5 h5'>{createdAt}</h5>
						{loggedId &&
							loggedId === reviewer.id && ( //TODO: Revisar
								<a className='col-1 fa-lg' href='/'>
									<PencilFill onClick={() => goToEditReview()} className='color-action' />
								</a>
							)}
					</Row>
					<Rating rating={rating} timesReviewed={props.timesReviewed} />
					<p className='text-muted mt-2'>{message}</p>
				</div>
			)}
		</>
	);
}

export default Review;
