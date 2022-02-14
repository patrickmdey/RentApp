import { useListCategories } from '../../api/categories/categoriesSlice';
import CategoryCard from './CategoryCard';
import { Card, Col, Container, Row } from 'react-bootstrap';
import { strings } from '../../i18n/i18n';

export default function CategoriesList() {
	const { data, isSuccess } = useListCategories();
	return (
		<Card className='p-3 bg-color-secondary card-style'>
			<Card.Title as='h3'> {strings.collection.categories.searchByCategories}</Card.Title>
			<hr />
			<Card.Body>
				<Container>
					<Row xs={7} className='w-100'>
						{isSuccess &&
							data &&
							data.map((cat) => (
								<Col key={cat.id}>
									<CategoryCard {...cat} />
								</Col>
							))}
					</Row>
				</Container>
			</Card.Body>
		</Card>
	);
}
