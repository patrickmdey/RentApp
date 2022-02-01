import { useState } from 'react';
import { Form, Card, FormLabel, Button } from 'react-bootstrap';
import { useListCategories } from '../features/api/categories/categoriesSlice';
import { strings } from '../i18n/i18n';
import { Category } from '../features/api/categories/types';
import { useForm } from 'react-hook-form';
import { useCreateArticle } from '../features/api/articles/articlesSlice';
import FormInput from './Forms/FormInput';

interface ArticleForm {
	title: string;
	description: string;
	pricePerDay: number;
	rating: number;
	categories: number[];
	ownerId: number; // TODO: esto en realidad no va aca creo, se maneja con el token del usuario
	images: FileList;
}

function CreateArticleForm() {
	const {
		register,
		handleSubmit,
		formState: { errors }
	} = useForm<ArticleForm>({
		defaultValues: {
			title: '',
			description: '',
			pricePerDay: 0,
			rating: 0,
			categories: [],
			ownerId: 0,
			images: undefined
		}
	});

	const [createArticle] = useCreateArticle();
	// console.log(result);

	function onSubimt(data: ArticleForm) {
		console.log(data);
		// createArticle({ ...data });
	}

	// const [name, setName] = useState("");
	// const [description, setDescription] = useState("");
	const [articleCategories, setArticleCategories] = useState([]);
	// const [images, setImages] = useState([]);

	const handleChange = (event: any) => {
		const { checked, value } = event.currentTarget;

		setArticleCategories((prev: any) =>
			checked ? [...prev, value] : prev.filter((val: Category) => val !== value)
		);
	};

	// const submitArticle = (e: any) => {
	//   e.preventDefault();
	//   const article = { name, description, articleCategories };
	//   console.log(article); //TODO: Agregar lo de las imagenes y llamar a use createArticle()
	// };

	const { data: categories, isSuccess } = useListCategories();

	return (
		<Card className='card-style create-card'>
			{isSuccess && categories && (
				<Card.Body className='form-container'>
					<Card.Title>
						<h3 className='fw-bold'>{strings.collection.article.createArticle.title}</h3>
						<hr></hr>
					</Card.Title>
					<Form onSubmit={handleSubmit(onSubimt)}>
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
							{/* <p className='error mt-2'>⚠{strings.collection.article.createArticle.errors.pricePerDay}</p> */}
						</div>
						<div>
							<Form.Label className='lead'>
								{strings.collection.article.createArticle.selectCategory}
							</Form.Label>
							<div className='category-list-container my-2 mx-1'>
								{categories.map((cat, i) => (
									<Form.Check
										key={i.toString()}
										id={i.toString()}
										value={cat.description}
										type='checkbox'
										checked={articleCategories.some((val) => val === cat.description)}
										onChange={handleChange}
										label={cat.description}
									></Form.Check>
								))}
							</div>
						</div>
						<div>
							<FormInput
								register={register}
								label={strings.collection.article.createArticle.selectImage}
								error={errors.images}
								errorMessage={strings.collection.article.createArticle.errors.images}
								name='images'
								type='file'
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
