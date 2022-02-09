import { skipToken } from '@reduxjs/toolkit/dist/query';
import { useState } from 'react';
import { Card, Container } from 'react-bootstrap';
import Articles from '../components/Profile/Articles';
import ProfileCard from '../components/Profile/ProfileCard';
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

	// TODO: delete user
	return (
		<Container>
			{user && location && (
				<ProfileCard disabled={disabled} setDisabled={setDisabled} location={location} user={user} />
			)}
			{user && disabled && (
				<Card className='w-100 p-3 mt-3'>
					<Articles user={user} />
				</Card>
			)}
		</Container>
	);
}
