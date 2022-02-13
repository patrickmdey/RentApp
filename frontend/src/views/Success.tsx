import { useNavigate } from 'react-router-dom';
import { strings } from '../i18n/i18n';
import successImage from '../assets/images/success.png';

function Success() {
	const navigate = useNavigate();

	return (
		<div className='main-container min-height'>
			<div className='card shadow card-style'>
				<div className='d-flex justify-content-center'>
					<img src={successImage} height='280px' width='280px' alt='success'></img>
				</div>
				<h2 className='h2'>{strings.collection.article.requestArticle.successTitle}</h2>
				<a className='lead' onClick={() => navigate('/marketplace')}>
					{strings.collection.article.requestArticle.successSubtitle}
				</a>
			</div>
		</div>
	);
}

export default Success;
