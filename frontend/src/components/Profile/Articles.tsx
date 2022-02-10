import { Tab, Tabs } from 'react-bootstrap';
import { useListArticles } from '../../features/api/articles/articlesSlice';
import { User } from '../../features/api/users/types';
import { strings } from '../../i18n/i18n';
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
				<Tab eventKey='published' title={strings.collection.profile.createdArticles}>
					<ArticleCardList articles={publishedArticles?.data || []} articlesPerRow={3} />
				</Tab>
			)}
			<Tab eventKey='rented' title={strings.collection.profile.rentedArticles}>
				<ArticleCardList articles={rentedArticles?.data || []} articlesPerRow={3} />
			</Tab>
		</Tabs>
	);
}
