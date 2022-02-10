function ErrorComponent(props: { error: number; message: string }) {
	const { error, message } = props;
	return (
		<div className='d-flex justify-content-center align-items-center'>
			<img src='http://pawserver.it.itba.edu.ar/paw-2021b-3/resources/image/error.png' />
			<h3 className='fw-bold'>Error {error}</h3>
			<p className='lead'>{message}</p>
		</div>
	);
}

export default ErrorComponent;
