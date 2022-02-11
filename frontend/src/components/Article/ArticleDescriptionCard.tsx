import { Card } from 'react-bootstrap';
import { strings } from '../../i18n/i18n';

function ArticleDescriptionCard(props: { articleDescription: String }) {
	return (
		<Card className='card-style article-card'>
			<Card.Title as='h3'>{strings.collection.article.descriptionTitle}</Card.Title>
			<hr></hr>
			<Card.Body className='lead'>{props.articleDescription}</Card.Body>
		</Card>
	);
}

export default ArticleDescriptionCard;
