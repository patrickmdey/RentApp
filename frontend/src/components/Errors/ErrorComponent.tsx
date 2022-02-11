function ErrorComponent(props: {
	error: number | 'CUSTOM_ERROR' | 'FETCH_ERROR' | 'PARSING_ERROR';
	message: string | undefined;
}) {
	const { error, message } = props;
	return (
		<div className='d-flex flex-column justify-content-center align-items-center'>
			<img src='http://pawserver.it.itba.edu.ar/paw-2021b-3/resources/image/error.png' />
			<h3 className='fw-bold'>Error {error}</h3>
			<p className='lead'>{message}</p>
		</div>
	);
}

export default ErrorComponent;
