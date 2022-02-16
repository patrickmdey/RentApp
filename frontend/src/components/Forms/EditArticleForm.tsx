import { skipToken } from '@reduxjs/toolkit/dist/query';
import { useEffect, useState } from 'react';
import { Button, Card, Form, Stack } from 'react-bootstrap';
import { useForm } from 'react-hook-form';
import { useNavigate, useParams } from 'react-router-dom';
import { useFindArticle, useUpdateArticle } from '../../api/articles/articlesSlice';
import { useListCategories, useListCategoriesFromArticle } from '../../api/categories/categoriesSlice';
import { useFindUser } from '../../api/users/usersSlice';
import useUserId from '../../hooks/useUserId';
import { strings } from '../../i18n/i18n';
import FormCheckbox from '../FormInputs/FormCheckbox';
import FormInput from '../FormInputs/FormInput';

interface ArticleForm {
	title: string;
	description: string;
	pricePerDay: number;
	categories: number[];
}

function UnauthorizedCard(props: { id?: string }) {
	const navigate = useNavigate();
	return (
		<>
			<Card.Header>{strings.collection.article.editArticle.errors.unauthorized}</Card.Header>
			<Card.Footer>
				<Stack direction='horizontal'>
					<Button variant='outline-secondary' onClick={() => navigate(-1)} className='ms-auto me-2'>
						{strings.collection.article.editArticle.errors.back}
					</Button>
					<Button onClick={() => navigate(`/articles/${props.id}`)}>
						{strings.collection.article.editArticle.errors.view}
					</Button>
				</Stack>
			</Card.Footer>
		</>
	);
}

export default function EditArticleForm() {
	const {
		register,
		handleSubmit,
		formState: { errors }
	} = useForm<ArticleForm>();

	const navigate = useNavigate();
	const [editArticle, result] = useUpdateArticle();

	useEffect(() => {
		if (result.isSuccess) navigate(`/articles/${id}`);
	}, [result]);

	const { id } = useParams();
	const userId = useUserId();

	const { data: article } = useFindArticle(`articles/${id}`);
	const { data: user } = useFindUser(userId ? `users/${userId}` : skipToken);
	const { data: categories } = useListCategories();
	const { data: articleCategories } = useListCategoriesFromArticle(article ? article.categoriesUrl : skipToken);
	const [submited, setSubmited] = useState(false);

	function onSubmit(data: ArticleForm) {
		editArticle({ url: `articles/${id}`, ...data });
	}

	console.log(article, user);

	return (
		<Card className='card-style create-card'>
			{article != null && categories != null && articleCategories && user && (
				<>
					{article.ownerUrl != user.url ? (
						<UnauthorizedCard id={id} />
					) : (
						<Form onSubmit={handleSubmit(onSubmit, () => setSubmited(true))}>
							<div className='my-2'>
								<FormInput
									register={register}
									label={strings.collection.article.createArticle.articleName}
									name='title'
									type='text'
									error={errors.title}
									value={article.title}
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
									as='textarea'
									value={article.description}
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
									value={article.pricePerDay.toString()}
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
											checked={articleCategories.findIndex((c) => c.id === cat.id) != -1}
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
							<Stack direction='horizontal'>
								<Button onClick={() => navigate(-1)} variant='outline-danger' className='ms-auto me-2'>
									{strings.collection.forms.cancel}
								</Button>
								<Button type='submit' variant='primary'>
									{strings.collection.forms.save}
								</Button>
							</Stack>
						</Form>
					)}
				</>
			)}
		</Card>
	);
}
