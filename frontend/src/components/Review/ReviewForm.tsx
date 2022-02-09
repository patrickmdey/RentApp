import { Button, Card, Col, Form, FormControl, FormGroup, FormLabel, InputGroup } from 'react-bootstrap';
import { strings } from '../../i18n/i18n';
import { Article } from '../../features/api/articles/types';
import { useForm, UseFormRegister } from 'react-hook-form';
import { useCreateReview } from '../../features/api/reviews/reviewsSlice';
import useUserId from '../../hooks/useUserId';
import FormInput from '../Forms/FormInput';
import { useListImages } from '../../features/api/images/imagesSlice';
import { Rating as SimpleStarRating } from 'react-simple-star-rating';
import { useEffect, useState } from 'react';

interface ReviewForm {
	rating: number;
	message: string;
	articleId: number;
	renterId: number;
}

function ReviewForm(props: { article: Article }) {
	const { article } = props;
	const loggedUserId = useUserId();
	const [rating, setRating] = useState(0);

	const {
		register,
		handleSubmit,
		setValue,
		formState: { errors }
	} = useForm<ReviewForm>({
		defaultValues: {
			rating: rating,
			message: '',
			articleId: article.id,
			renterId: loggedUserId || undefined
		}
	});

	useEffect(() => setValue('rating', rating), [rating]);

	const { data: articleImages, isSuccess } = useListImages(article.imagesUrl);

	const [createReview] = useCreateReview();

	function onSubmit(data: ReviewForm) {
		console.log(data);
		// createReview({ ...data });
	}

	return (
		<>
			{isSuccess && articleImages && (
				<Card>
					<Card.Body className='form-container'>
						<Card.Title>
							<h3 className='fw-bold'>{strings.collection.review.reviews}</h3>
							<hr />
						</Card.Title>
						<div className='d-flex'>
							<Col md={3} lg={3}>
								<img
									className='img-thumbnail rounded-start article-img'
									src={articleImages[0].url.toString()}
									alt='articlePicture'
									id='main-img'
								/>
							</Col>
							<Col md={1} lg={1} />
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
											placeholder={
												strings.collection.article.createArticle.articleDescriptionLabel
											}
											validation={{
												required: true,
												maxLength: 310,
												minLength: 10
											}}
										/>
									</div>
									<Button type='submit' className='rounded btn-block bg-color-action btn-dark'>
										{strings.collection.register.confirmButton}
									</Button>
								</Form>
							</Col>
							<Col md={1} lg={1} />
						</div>
					</Card.Body>
				</Card>
			)}
		</>
	);
}

export default ReviewForm;
