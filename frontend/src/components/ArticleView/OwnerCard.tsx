import { Card, Row, Nav } from 'react-bootstrap';
import { User } from '../../api/users/types';
import { strings } from '../../i18n/i18n';
import Avatar from 'react-avatar';
import { useNavigate } from 'react-router';

function OwnerCard(props: { owner: User | undefined }) {
	const navigate = useNavigate();
	return (
		<Card className='card-style'>
			<Card.Title as='h3'>{strings.collection.article.ownerCardTitle}</Card.Title>
			<hr />
			<Row className='align-items-center'>
				<Avatar round='100%' src={props.owner?.imageUrl} />
				<span className='lead col-8'>
					{props.owner && (
						<Nav.Item as='a'>
							<Nav.Link
								onClick={() =>
									navigate(`/marketplace?user=${props.owner !== undefined && props.owner.id}`)
								}
							>
								<span>
									{props.owner.firstName} {props.owner.lastName}
								</span>
							</Nav.Link>
						</Nav.Item>
					)}
				</span>
			</Row>
		</Card>
	);
}

export default OwnerCard;
