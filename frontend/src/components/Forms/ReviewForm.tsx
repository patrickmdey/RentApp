import { Button, Card, Col, Form, FormControl, FormGroup, FormLabel, InputGroup, Row, Stack } from 'react-bootstrap';
import { strings } from '../../i18n/i18n';
import { Article } from '../../api/articles/types';
import { useForm, UseFormRegister } from 'react-hook-form';
import { useCreateReview, useUpdateReview } from '../../api/reviews/reviewsSlice';
import useUserId from '../../hooks/useUserId';
import FormInput from '../FormInputs/FormInput';
import { useListImages } from '../../api/images/imagesSlice';
import { Rating as SimpleStarRating } from 'react-simple-star-rating';
import { useEffect, useState } from 'react';
import { Review } from '../../api/reviews/types';
import { useNavigate } from 'react-router-dom';
import { userTypes } from '../../views/Article';
import { useFindUser } from '../../api/users/usersSlice';

interface ReviewForm {
	rating: number;
	message: string;
	articleId: number;
	renterId: number;
}

function UnauthorizedCard() {
	const navigate = useNavigate();
	return (
		<Card>
			<Card.Body>
				<Card.Title>{strings.collection.review.unauthorized}</Card.Title>
				<Card.Footer>
					<Stack direction='horizontal'>
						<Button className='ms-auto' onClick={() => navigate(-1)}>
							{strings.collection.article.editArticle.errors.back}
						</Button>
					</Stack>
				</Card.Footer>
			</Card.Body>
		</Card>
	);
}

function ReviewForm(props: { article: Article; review?: Review }) {
	const { article, review } = props;
	const loggedUserId = useUserId();

	const [rating, setRating] = useState(review === undefined ? 1 : review.rating);
	const [message, setMessage] = useState(review === undefined ? '' : review.message);

	const { data: loggedUser } = useFindUser(`users/${loggedUserId}`);

	const {
		register,
		handleSubmit,
		setValue,
		formState: { errors }
	} = useForm<ReviewForm>({
		defaultValues: {
			rating: rating,
			message: message,
			articleId: article.id,
			renterId: loggedUserId || undefined
		}
	});

	useEffect(() => setValue('rating', rating), [rating]);
	useEffect(() => setValue('message', message), [message]);

	const { data: articleImages, isSuccess, isLoading } = useListImages(article.imagesUrl);

	const [createReview, createResult] = useCreateReview();
	const [updateReview, updateResult] = useUpdateReview();

	const navigate = useNavigate();
	useEffect(() => {
		if (createResult.isSuccess || updateResult.isSuccess) {
			navigate(`/articles/${article.id}`);
		}
	}, [createResult, updateResult]);

	function onSubmit(form: ReviewForm) {
		if (review == null) createReview({ ...form });
		else updateReview({ url: review.url.toString(), rating: rating, message: form.message });
	}

	if (review != null && loggedUser != null && review.renterUrl != loggedUser.url) {
		return <UnauthorizedCard />;
	}

	return (
		<Card>
			<Card.Body className='form-container'>
				<Card.Title>
					<h3 className='fw-bold'>{strings.collection.review.reviews}</h3>
					<hr />
				</Card.Title>
				<div className='d-flex'>
					<Col md={3} lg={3}>
						{isSuccess && articleImages && (
							<img
								className='img-thumbnail rounded-start article-img'
								src={articleImages[0].url.toString()}
								alt='articlePicture'
								id='main-img'
							/>
						)}
					</Col>
					<Col md={2} lg={2} />
					<Col md={7} lg={7}>
						<h3 className='mb-2'>{article.title}</h3>
						<Form onSubmit={handleSubmit(onSubmit)}>
							<div className='my-2'>
								<SimpleStarRating
									ratingValue={rating * 20}
									onClick={(r) => setRating(r / 20)}
									initialValue={0}
								/>
							</div>
							<div className='my-2'>
								<FormInput
									register={register}
									label={strings.collection.article.createArticle.articleDescription}
									name='message'
									type='text'
									error={errors.message}
									errorMessage={strings.collection.article.createArticle.errors.description}
									placeholder={strings.collection.article.createArticle.articleDescriptionLabel}
									validation={{
										required: true,
										maxLength: 310,
										minLength: 10
									}}
								/>
							</div>
							<Row>
								<Button type='submit' className='rounded btn-block bg-color-action btn-dark mt-3'>
									{strings.collection.register.confirmButton}
								</Button>
							</Row>
						</Form>
					</Col>
				</div>
			</Card.Body>
		</Card>
	);
}

export default ReviewForm;
