import { Card } from 'react-bootstrap';
import { LinkContainer } from 'react-router-bootstrap';

interface CategoryProps {
	description: string;
	url: string;
	imageUrl: string;
}
export default function CategoryCard(props: CategoryProps) {
	return (
		<LinkContainer to={props.url}>
			<Card>
				<Card.Img variant='top' src={props.imageUrl} alt={props.description} height='100rem' />
				<Card.Body className='text-center'>
					<Card.Title>{props.description}</Card.Title>
				</Card.Body>
			</Card>
		</LinkContainer>
	);
}
