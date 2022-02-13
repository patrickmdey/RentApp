import { useListCategories } from '../../api/categories/categoriesSlice';
import CategoryCard from './CategoryCard';
import { Col, Container, Row } from 'react-bootstrap';
import { strings } from '../../i18n/i18n';

export default function CategoriesList() {
	const { data, isSuccess } = useListCategories();
	return (
		<div className='bg-color-secondary pt-3' style={{ width: '100vw' }}>
			<Container>
				<h3 className='text-center mt-0 pt-0'>{strings.collection.categories.searchByCategories}</h3>
				<Row className='g-5 pb-4'>
					{isSuccess &&
						data &&
						data.map((cat) => (
							<Col key={cat.url.toString()}>
								<CategoryCard key={cat.url.toString()} {...cat} />
							</Col>
						))}
				</Row>
			</Container>
		</div>
	);
}
