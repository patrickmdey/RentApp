import { useListCategories } from '../features/api/categories/categoriesSlice';
import CategoryCard from './CategoryCard';
import { Col, Row } from 'react-bootstrap';
import { strings } from '../i18n/i18n';

export default function CategoriesList() {
	const { data, isSuccess } = useListCategories();
	return (
		<div className='bg-color-secondary w-100 pt-3'>
			<h3 className='text-center'>{strings.collection.categories.searchByCategories}</h3>
			<Row md={data ? data.length : 1} className='landing-category-container'>
				{isSuccess &&
					data &&
					data.map((cat) => (
						<Col key={cat.url.toString()}>
							<CategoryCard key={cat.url.toString()} {...cat} />
						</Col>
					))}
			</Row>
		</div>
	);
}
