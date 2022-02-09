import React, { useState } from 'react';
import { Button, Card, Stack } from 'react-bootstrap';
import { Pencil, Lock, Trash } from 'react-bootstrap-icons';
import { Location } from '../../features/api/locations/types';
import { User } from '../../features/api/users/types';
import PasswordForm from './PasswordForm';
import ProfileForm from './ProfileForm';

export default function ProfileCard(props: {
	disabled: boolean;
	setDisabled: React.Dispatch<React.SetStateAction<boolean>>;
	user: User;
	location: Location;
}) {
	const { disabled, setDisabled, user, location } = props;
	const [changingPassword, setChangingPassword] = useState(false);
	return (
		<Card className='w-100'>
			<Card.Header>
				<Stack direction='horizontal'>
					<Card.Title className='mb-0'>My Profile</Card.Title>
					{disabled && (
						<div className='ms-auto'>
							<Button variant='link' onClick={() => setDisabled(false)}>
								<Pencil />
							</Button>
							<Button
								variant='link'
								onClick={() => {
									setChangingPassword(true);
									setDisabled(false);
								}}
							>
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
				{user && location && changingPassword ? (
					<PasswordForm
						user={user}
						onDone={() => {
							setChangingPassword(false);
							setDisabled(true);
						}}
					/>
				) : (
					<ProfileForm
						disabled={disabled}
						onDone={() => setDisabled(true)}
						location={location.id}
						{...user}
					/>
				)}
			</Card.Body>
		</Card>
	);
}
