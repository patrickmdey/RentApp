import { Tab, Tabs } from 'react-bootstrap';
import { useListArticles } from '../../features/api/articles/articlesSlice';
import ArticleCardList from '../ArticleCardList';

export default function Articles(props: { userId: number }) {
	const {
		data: publishedArticles,
		isSuccess,
		error
	} = useListArticles({
		user: props.userId
	});

	return (
		<Tabs defaultActiveKey='published' id='uncontrolled-tab-example' className='mb-3'>
			<Tab eventKey='published' title='Published Articles'>
				<ArticleCardList articles={publishedArticles?.data || []} articlesPerRow={3} />
			</Tab>
			<Tab eventKey='rented' title='Rented Articles'>
				<ArticleCardList articles={[]} articlesPerRow={3} />
			</Tab>
		</Tabs>
	);
}
