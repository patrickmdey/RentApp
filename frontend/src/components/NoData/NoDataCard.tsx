import { Card } from 'react-bootstrap';

function NoDataCard(props: { title: string; subtitle: string }) {
	const { title, subtitle } = props;

	return (
		<Card className='card-style d-flex justify-content-center align-items-center mt-2'>
			<h3>{title}</h3>
			<p>{subtitle}</p>
		</Card>
	);
}

export default NoDataCard;
