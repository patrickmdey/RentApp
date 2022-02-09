import { Tab, Tabs } from 'react-bootstrap';
import { useListArticles } from '../../features/api/articles/articlesSlice';
import { User } from '../../features/api/users/types';
import ArticleCardList from '../ArticleCardList';

export default function Articles(props: { user: User }) {
	const user = props.user;
	const {
		data: publishedArticles,
		isSuccess,
		error
	} = useListArticles({
		user: user.id
	});

	const {
		data: rentedArticles,
		isSuccess: rentedIsSuccess,
		error: rentedError
	} = useListArticles({
		renter: user.id
	});

	const defaultActiveTab = user.owner ? 'published' : 'rented';

	return (
		<Tabs defaultActiveKey={defaultActiveTab} id='article_tabs' className='mb-3'>
			{user.owner && (
				<Tab eventKey='published' title='Published Articles'>
					<ArticleCardList articles={publishedArticles?.data || []} articlesPerRow={3} />
				</Tab>
			)}
			<Tab eventKey='rented' title='Rented Articles'>
				<ArticleCardList articles={rentedArticles?.data || []} articlesPerRow={3} />
			</Tab>
		</Tabs>
	);
}
