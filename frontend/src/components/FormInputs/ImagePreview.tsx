import { Button, Image } from 'react-bootstrap';
import { CircleFill, XCircle, XCircleFill } from 'react-bootstrap-icons';

export default function ImagePreview(props: { img: string; onDelete: Function }) {
	const { img, onDelete } = props;
	return (
		<div style={{ position: 'relative' }}>
			<Button
				variant='link'
				className='text-danger fs-6 p-0 m-0 text-decoration-none'
				style={{ position: 'absolute', top: -10, right: -10, height: '5px' }}
				onClick={(e) => {
					e.preventDefault();
					onDelete();
				}}
			>
				<XCircleFill className='color-rentapp-red' />
			</Button>
			<Image src={img} width={40} height={40} />
		</div>
	);
}
