import { Card } from 'react-bootstrap';
import { LinkContainer } from 'react-router-bootstrap';
import { Link } from 'react-router-dom';

interface CategoryProps {
	id: number;
	description: string;
	url: string;
	imageUrl: string;
}

export default function CategoryCard(props: CategoryProps) {
	return (
		<LinkContainer to={`/marketplace?category=${props.id}`}>
			<Card>
				<Card.Body>
					<Card.Img variant='top' src={props.imageUrl} alt={props.description} height='80rem' />
					<div className='d-flex text-center justify-content-center pt-3'>
						<Card.Title as='h4'>{props.description}</Card.Title>
					</div>
				</Card.Body>
			</Card>
		</LinkContainer>
	);
}
