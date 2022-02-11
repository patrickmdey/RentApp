import { Button, Image } from 'react-bootstrap';

export default function ImagePreview(props: { img: string; onDelete: Function }) {
	const { img, onDelete } = props;
	return (
		<div style={{ position: 'relative' }}>
			<Button
				variant='link'
				className='text-danger fs-4 p-0 m-0 text-decoration-none'
				style={{ position: 'absolute', top: -15, right: -5, height: '5px' }}
				onClick={(e) => {
					e.preventDefault();
					onDelete();
				}}
			>
				x
			</Button>
			<Image src={img} width={40} height={40} />
		</div>
	);
}
