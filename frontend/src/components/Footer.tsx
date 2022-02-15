import { Button, Card, Image, ListGroup, ListGroupItem } from 'react-bootstrap';
import { strings } from '../i18n/i18n';
import React from 'react';
import { useAppDispatch } from '../hooks';
import { setLanguage } from '../api/i18n/i18nSlice';

const logo = require('../assets/images/rentapp-favicon.png');

// function Footer(props: ReduxProps) {
export default function Footer() {
	const dispatch = useAppDispatch();

	function updateLanguage(language: string) {
		dispatch(setLanguage(language));
	}

	return (
		<footer className=' footer-style bg-color-primary'>
			<Card.Body className='row my-n4'>
				<div className='col-4 footer-image-col'>
					<Image src={logo} className='mt-n3' height='180px' width='auto' alt='RentApp' />
				</div>
				<div className='col-4'>
					<ListGroup as='ul' className='col-6 list-unstyled'>
						<h3 className='h3 fw-bold color-grey'>{strings.collection.footer.team}</h3>
						<ListGroupItem bsPrefix='color-grey fw-bold mt-2'>Santos Rosati</ListGroupItem>
						<ListGroupItem bsPrefix='color-grey fw-bold mt-2'>Matias Lombardi</ListGroupItem>
						<ListGroupItem bsPrefix='color-grey fw-bold mt-2'>Patrick Dey</ListGroupItem>
						<ListGroupItem bsPrefix='color-grey fw-bold mt-2'>Lucas Dell'Isola</ListGroupItem>
					</ListGroup>
				</div>

				<div className='col-4'>
					<ListGroup className='list-unstyled' as='ul'>
						<ListGroupItem bsPrefix='h3 fw-bold color-grey'>
							{strings.collection.footer.contact}
						</ListGroupItem>
						<ListGroupItem bsPrefix='lead color-grey'>
							{strings.collection.footer.email}:
							<a href='mailto:paw2021b3@gmail.com' className='color-secondary ms-1 pb-1'>
								paw2021b3@gmail.com
							</a>
						</ListGroupItem>
						<ListGroupItem bsPrefix='h3 fw-bold color-grey mt-3'>
							{strings.collection.footer.language}
						</ListGroupItem>
						<ListGroupItem bsPrefix=' '>
							<Button className='me-1 color-secondary' onClick={() => updateLanguage('en')}>
								{strings.collection.footer.english}
							</Button>
							<Button className='color-secondary' onClick={() => updateLanguage('es')}>
								{strings.collection.footer.spanish}
							</Button>
						</ListGroupItem>
					</ListGroup>
				</div>
			</Card.Body>
			<Card.Footer
				className='bg-color-secondary d-flex justify-content-center align-content-center'
				style={{ height: '40px' }}
			>
				<p className='fw-light'>PAW 2021b - Grupo 3</p>
			</Card.Footer>
		</footer>
	);
}
