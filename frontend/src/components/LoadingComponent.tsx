import { Spinner } from 'react-bootstrap';

function LoadingComponent() {
	return (
		<div className='d-flex flex-column justify-content-center align-items-center mt-3'>
			<div className='d-flex justify-content-center align-content-center'>
				<Spinner
					animation='border'
					role='status'
					className='color-secondary'
					style={{ height: '120px', width: '120px' }}
				/>
			</div>
		</div>
	);
}

export default LoadingComponent;
