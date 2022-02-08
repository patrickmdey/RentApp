import { useAppDispatch, useAppSelector } from '../hooks';
import { Container, Image, Nav, Navbar, NavDropdown } from 'react-bootstrap';
import { LinkContainer } from 'react-router-bootstrap';
import React from 'react';
import { BsBoxArrowInLeft, BsFillInboxFill, BsPersonFill } from 'react-icons/bs';
import { Link } from 'react-router-dom';
import { strings } from '../i18n/i18n';
import { setCredentials } from '../features/auth/authSlice';

import logo from '../images/rentapp-logo.png';

function RenderLoggedInNavBar(dispatch: Function) {
	const name = 'lucas';

	return (
		<NavDropdown title={name} className='active color-grey h6' id='collasible-nav-dropdown'>
			<NavDropdown.Item>
				<Link to='/proposals'>
					<BsFillInboxFill />
					{strings.collection.header.requests}
				</Link>
			</NavDropdown.Item>

			<NavDropdown.Item>
				<Link to='/profile'>
					<BsPersonFill />
					{strings.collection.header.profile}
				</Link>
			</NavDropdown.Item>

			<NavDropdown.Item onClick={() => dispatch(setCredentials({ token: null, rememberMe: false }))}>
				<BsBoxArrowInLeft />
				{strings.collection.header.logout}
			</NavDropdown.Item>
		</NavDropdown>
	);
}

function RenderLoggedOutNavBar() {
	return (
		<React.Fragment>
			<LinkContainer to='/login'>
				<Nav.Link className='active h6'>{strings.collection.header.login}</Nav.Link>
			</LinkContainer>

			<LinkContainer to='/register'>
				<Nav.Link className='active h6'>{strings.collection.header.signup}</Nav.Link>
			</LinkContainer>
		</React.Fragment>
	);
}

export default function Header() {
	const isAuthenticated = useAppSelector((state) => state.auth.token != null);
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
					<Nav className='ms-auto'>
						<LinkContainer to='/marketplace'>
							<Nav.Link className='active h6'>{strings.collection.header.marketplace}</Nav.Link>
						</LinkContainer>

						<LinkContainer to='/createArticle'>
							<Nav.Link className='active h6'>{strings.collection.header.publishArticle}</Nav.Link>
						</LinkContainer>

						{isAuthenticated ? RenderLoggedInNavBar(dispatch) : RenderLoggedOutNavBar()}
					</Nav>
				</Navbar.Collapse>
			</Container>
		</Navbar>
	);
}
