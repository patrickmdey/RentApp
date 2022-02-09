import { skipToken } from '@reduxjs/toolkit/dist/query';
import { useState } from 'react';
import { Button, Card, Container, Stack } from 'react-bootstrap';
import { Pencil, Lock, Trash } from 'react-bootstrap-icons';
import { useNavigate } from 'react-router-dom';
import Articles from '../components/Profile/Articles';
import ProfileForm from '../components/Profile/ProfileForm';
import { useFindLocation } from '../features/api/locations/locationsSlice';
import { useFindUser } from '../features/api/users/usersSlice';
import useUserId from '../hooks/useUserId';

export default function Profile() {
	const id = useUserId();
	if (id == null) throw new Error('Unauthorized');

	const {
		data: user,
		isLoading: userIsLoading,
		isSuccess: userIsSuccess
	} = useFindUser(new URL(`users/${id}`, process.env.REACT_APP_BASE_URL));

	const { data: location } = useFindLocation(userIsLoading || user == null ? skipToken : user.locationUrl);
	const [disabled, setDisabled] = useState(true);

	// TODO: edit password form, delete user, published / rented articles
	return (
		<Container>
			<Card className='w-100'>
				<Card.Header>
					<Stack direction='horizontal'>
						<Card.Title className='mb-0'>My Profile</Card.Title>
						{disabled && (
							<div className='ms-auto'>
								<Button variant='link'>
									<Pencil onClick={() => setDisabled(false)} />
								</Button>
								<Button variant='link'>
									<Lock />
								</Button>
								<Button variant='link' className='text-danger'>
									<Trash />
								</Button>
							</div>
						)}
					</Stack>
				</Card.Header>
				<Card.Body>
					{userIsSuccess && user && location && (
						<ProfileForm
							disabled={disabled}
							onCancel={() => setDisabled(true)}
							location={location.id}
							{...user}
						/>
					)}
					{userIsLoading && <h1>Loading...</h1>}
				</Card.Body>
			</Card>
			<Card className='w-100 p-4'>
				<Articles userId={id} />
			</Card>
		</Container>
	);
}
