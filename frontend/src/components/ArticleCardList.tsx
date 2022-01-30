import { Row, Col } from 'react-bootstrap';
import { Article } from '../features/api/articles/types';
import ArticleCard from './ArticleCard';

function ArticleCardList(props: { articles: Article[]; maxPage: Number; articlesPerRow: Number }) {
	const { articles, maxPage, articlesPerRow } = props;
	return (
		<div className='justify-content-center w-100'>
			<Row md={articlesPerRow as number} className='w-100 justify-content-start'>
				{articles.map((article) => (
					<Col>
						<ArticleCard key={article.url.toString()} {...article}></ArticleCard>
					</Col>
				))}
			</Row>
		</div>
	);
}

export default ArticleCardList;
