import { useEffect, useState } from 'react';
import { Form, Card, Button, FormControl } from 'react-bootstrap';
import { useListCategories } from '../features/api/categories/categoriesSlice';
import { strings } from '../i18n/i18n';
import { Category } from '../features/api/categories/types';
import { useForm } from 'react-hook-form';
import { useCreateArticle } from '../features/api/articles/articlesSlice';
import FormInput from './Forms/FormInput';
import FormCheckbox from './Forms/FormCheckbox';
import { useNavigate } from 'react-router-dom';
import MultipleImageInput from './Forms/MultipleImageInput';

interface ArticleForm {
	title: string;
	description: string;
	pricePerDay: number;
	categories: number[];
	images: File[];
}

function CreateArticleForm() {
	const {
		register,
		handleSubmit,
		setValue,
		formState: { errors }
	} = useForm<ArticleForm>({
		defaultValues: {
			title: '',
			description: '',
			pricePerDay: 0,
			categories: [],
			images: []
		}
	});

	const [submited, setSubmited] = useState(false);

	useEffect(() => {
		register('images', { required: true, min: 1, max: 5 });
	}, []);

	const [createArticle, result] = useCreateArticle();
	const navigate = useNavigate();
	useEffect(() => {
		if (result.isSuccess) navigate(`/articles/${result.data}`);
	}, [result]);

	function onSubmit(data: ArticleForm) {
		console.log(data);
		createArticle(data);
	}

	const { data: categories, isSuccess } = useListCategories();

	return (
		<Card className='card-style create-card'>
			{isSuccess && categories && (
				<Card.Body className='form-container'>
					<Card.Title>
						<h3 className='fw-bold'>{strings.collection.article.createArticle.title}</h3>
						<hr />
					</Card.Title>
					<Form onSubmit={handleSubmit(onSubmit, () => setSubmited(true))}>
						<div className='my-2'>
							<FormInput
								register={register}
								label={strings.collection.article.createArticle.articleName}
								name='title'
								type='text'
								error={errors.title}
								errorMessage={strings.collection.article.createArticle.errors.title}
								placeholder={strings.collection.article.createArticle.articleNameLabel}
								validation={{ required: true, maxLength: 50, minLength: 5 }}
							/>
						</div>
						<div className='my-2'>
							<FormInput
								register={register}
								label={strings.collection.article.createArticle.articleDescription}
								name='description'
								type='text'
								error={errors.description}
								errorMessage={strings.collection.article.createArticle.errors.description}
								placeholder={strings.collection.article.createArticle.articleDescriptionLabel}
								validation={{ required: true, maxLength: 700, minLength: 10 }}
							/>
						</div>
						<div className='my-2'>
							<FormInput
								register={register}
								label={strings.collection.article.createArticle.pricePerDay}
								name='pricePerDay'
								error={errors.pricePerDay}
								errorMessage={strings.collection.article.createArticle.errors.pricePerDay}
								type='number'
								prependIcon='$'
								validation={{ min: 1 }}
							/>
						</div>
						<div>
							<Form.Label>{strings.collection.article.createArticle.selectCategory}</Form.Label>
							<div className='category-list-container my-2 mx-1'>
								{categories.map((cat) => (
									<FormCheckbox
										key={cat.id}
										register={register}
										name='categories'
										validation={{ required: true }}
										label={cat.description}
										value={cat.id}
									/>
								))}
							</div>
							{errors.categories && submited && (
								<p className='text-danger fs-7 fw-light'>
									{strings.collection.article.createArticle.errors.categories.required}
								</p>
							)}
						</div>
						<div>
							<MultipleImageInput
								register={register}
								accept='image/*'
								name='images'
								max={5}
								setValue={(v: File[]) => setValue('images', v, { shouldValidate: true })}
								label={strings.collection.article.createArticle.selectImage}
								hasError={errors.images != null && submited}
								errorMessage={strings.collection.article.createArticle.selectImage}
							/>
						</div>
						<div className='d-grid gap-2'>
							<Button className='bg-color-action btn-dark mt-3 mb-2' type='submit'>
								{strings.collection.article.createArticle.create}
							</Button>
						</div>
					</Form>
				</Card.Body>
			)}
		</Card>
	);
}

export default CreateArticleForm;
