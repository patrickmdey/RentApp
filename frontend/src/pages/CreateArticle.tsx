import CreateArticleForm from '../components/CreateArticleForm';
import { Container } from 'react-bootstrap';
import { Helmet } from 'react-helmet-async';
import { strings } from '../i18n/i18n';
export default function CreateArticle() {
	return (
		<>
			<Helmet>
				<title>{strings.collection.pageTitles.createArticle}</title>
			</Helmet>
			<Container style={{ width: '50%' }} className=' min-height'>
				<CreateArticleForm />
			</Container>
		</>
	);
}
