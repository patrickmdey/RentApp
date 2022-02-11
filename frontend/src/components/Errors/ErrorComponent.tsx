import errorImage from '../../images/error.png';
function ErrorComponent(props: {
	error: number | 'CUSTOM_ERROR' | 'FETCH_ERROR' | 'PARSING_ERROR';
	message: string | undefined;
}) {
	const { error, message } = props;
	return (
		<div className='d-flex flex-column justify-content-center align-items-center'>
			<img src={errorImage} />
			<h3 className='fw-bold'>Error {error}</h3>
			<p className='lead'>{message}</p>
		</div>
	);
}

export default ErrorComponent;
