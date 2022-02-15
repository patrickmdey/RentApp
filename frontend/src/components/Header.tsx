import { useAppDispatch, useAppSelector } from '../hooks';
import { Badge, Button, Container, Image, Nav, Navbar, NavDropdown } from 'react-bootstrap';
import { LinkContainer } from 'react-router-bootstrap';
import React, { useEffect, useState } from 'react';
import { BsBoxArrowInLeft, BsFillInboxFill, BsPersonFill } from 'react-icons/bs';
import { strings } from '../i18n/i18n';
import { setCredentials } from '../api/auth/authSlice';

import logo from '../assets/images/rentapp-logo.png';
import { useFindUser } from '../api/users/usersSlice';
import useUserId from '../hooks/useUserId';
import { useNavigate } from 'react-router';

function LoggedInNavBar(props: { userId: number; dispatch: Function }) {
	const { userId, dispatch } = props;
	const [name, setName] = useState('');
	const [requestAmount, setRequestAmount] = useState(0);
	const { data: user } = useFindUser(`users/${userId}`);
	const navigate = useNavigate();

	useEffect(() => setName(user ? user.firstName : ''), [user]);

	useEffect(
		() =>
			setRequestAmount(
				user ? user.acceptedRequestAmount + user.declinedRequestAmount + user.pendingRequestAmount : 0
			),
		[user]
	);

	return (
		<NavDropdown
			as='h6'
			title={
				<div style={{ display: 'inline-block' }}>
					<div className='d-flex justify-content-center align-items-center'>
						<p className='color-grey fw-bold'>{name}</p>
						{requestAmount > 0 && (
							<p className='ms-1'>
								<Badge className='bg-rentapp-red'>{requestAmount}</Badge>
							</p>
						)}
					</div>
				</div>
			}
			className='active color-grey'
			id='collasible-nav-dropdown'
		>
			<NavDropdown.Item onClick={() => navigate('proposals')} style={{ display: 'inline-block' }}>
				<div className='d-flex justify-content-center align-items-center text-center'>
					<BsFillInboxFill className='me-1' />
					<p>{strings.collection.header.requests}</p>
					{requestAmount > 0 && (
						<p className=''>
							<Badge className='bg-rentapp-red ms-1'>{requestAmount}</Badge>
						</p>
					)}
				</div>
			</NavDropdown.Item>

			<NavDropdown.Item onClick={() => navigate('profile')} className='d-flex align-content-center'>
				<p>
					<BsPersonFill className='me-1' />
				</p>
				<p>{strings.collection.header.profile}</p>
			</NavDropdown.Item>

			<NavDropdown.Item
				className='d-flex align-content-center'
				onClick={() => dispatch(setCredentials({ token: null, rememberMe: false }))}
			>
				<p>
					<BsBoxArrowInLeft className='me-1' />
				</p>
				<p>{strings.collection.header.logout}</p>
			</NavDropdown.Item>
		</NavDropdown>
	);
}

function LoggedOutNavBar() {
	return (
		<React.Fragment>
			<LinkContainer to='/login'>
				<Nav.Link as='h6' className='active fw-bold'>
					{strings.collection.header.login}
				</Nav.Link>
			</LinkContainer>

			<LinkContainer to='/register'>
				<Nav.Link as='h6' className='active fw-bold'>
					{strings.collection.header.signup}
				</Nav.Link>
			</LinkContainer>
		</React.Fragment>
	);
}

export default function Header() {
	const userId = useUserId();
	const dispatch = useAppDispatch();

	return (
		<Navbar collapseOnSelect expand='lg' bg='primary' className='mb-2' variant='dark'>
			<Container>
				<LinkContainer to='/'>
					<Navbar.Brand>
						<Image alt='rentapp' src={logo} height='50px' />
					</Navbar.Brand>
				</LinkContainer>
				<Navbar.Toggle aria-controls='responsive-navbar-nav' />
				<Navbar.Collapse className='mt-2' id='responsive-nav-bar'>
					<Nav className='ms-auto d-flex align-items-center'>
						<LinkContainer to='/marketplace'>
							<Nav.Link as='h6' className='active fw-bold nav-bar-link'>
								{strings.collection.header.marketplace}
							</Nav.Link>
						</LinkContainer>

						<LinkContainer to='/createArticle'>
							<Nav.Link as='h6' className='active fw-bold nav-bar-link'>
								{strings.collection.header.publishArticle}
							</Nav.Link>
						</LinkContainer>

						{userId != null ? <LoggedInNavBar dispatch={dispatch} userId={userId} /> : <LoggedOutNavBar />}
					</Nav>
				</Navbar.Collapse>
			</Container>
		</Navbar>
	);
}
