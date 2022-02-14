import {useListCategories} from '../../api/categories/categoriesSlice';
import CategoryCard from './CategoryCard';
import {Card, Col, Container, Row} from 'react-bootstrap';
import {strings} from '../../i18n/i18n';

export default function CategoriesList() {
	const { data, isSuccess } = useListCategories();
	return (
		<Card className="card-style category-card-list bg-color-secondary">
			<Container>
				<Card.Title as="h3"> {strings.collection.categories.searchByCategories}</Card.Title>
				<hr/>
				<Row md={4} lg={4} className='justify-content-center'>
					{isSuccess &&
						data &&
						data.map((cat) => (
							<Col key={cat.id}>
								<CategoryCard {...cat} />
							</Col>
						))}
				</Row>
			</Container>
		</Card>
	);
}
