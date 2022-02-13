import {Card, Row} from 'react-bootstrap';
import {User} from '../../api/users/types';
import {strings} from '../../i18n/i18n';

function OwnerCard(props: { owner: User | undefined }) {
    return (
        <Card className='card-style'>
            <Card.Title as='h3'>{strings.collection.article.ownerCardTitle}</Card.Title>
            <hr/>
            <Row className='align-items-center'>
                {/*TODO: hacerla un circulo*/}
                <div className='avatar-container col-4'>
                    <img width='50%' src={props.owner?.imageUrl}/>
                </div>
                <span className='lead col-8'>
					{props.owner && (
                        <span>
							{props.owner.firstName} {props.owner.lastName}
						</span>
                    )}
				</span>
            </Row>
        </Card>
    );
}

export default OwnerCard;
