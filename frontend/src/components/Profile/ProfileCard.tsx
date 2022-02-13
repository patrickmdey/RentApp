import React, { useEffect, useState } from 'react';
import { Button, Card, Modal, ModalBody, ModalFooter, ModalTitle, Stack } from 'react-bootstrap';
import { Pencil, Lock, Trash } from 'react-bootstrap-icons';
import ModalHeader from 'react-bootstrap/esm/ModalHeader';
import { useNavigate } from 'react-router-dom';
import { Location } from '../../api/locations/types';
import { User } from '../../api/users/types';
import { useDeleteUser } from '../../api/users/usersSlice';
import { setCredentials } from '../../api/auth/authSlice';
import { useAppDispatch } from '../../hooks';
import PasswordForm from './PasswordForm';
import ProfileForm from './ProfileForm';
import { strings } from '../../i18n/i18n';

export default function ProfileCard(props: {
	disabled: boolean;
	setDisabled: React.Dispatch<React.SetStateAction<boolean>>;
	user: User;
	location: Location;
}) {
	const { disabled, setDisabled, user, location } = props;
	const [changingPassword, setChangingPassword] = useState(false);
	const [showModal, setShowModal] = useState(false);
	const [deleteUser, deleteUserResult] = useDeleteUser();
	const dispatch = useAppDispatch();
	const navigate = useNavigate();

	useEffect(() => {
		if (deleteUserResult.isSuccess) {
			dispatch(setCredentials({ token: null, rememberMe: false }));
			navigate('/login');
		}
	}, [deleteUserResult]);

	return (
		<>
			<Card className='w-100'>
				<Card.Header>
					<Stack direction='horizontal'>
						<Card.Title className='mb-0'>{strings.collection.profile.profile}</Card.Title>
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
								<Button variant='link' className='text-danger' onClick={() => setShowModal(true)}>
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
			<Modal show={showModal}>
				<ModalHeader>{strings.collection.profile.deleteForm.message}</ModalHeader>
				<ModalBody>
					<p>{strings.collection.profile.deleteForm.confirmation}</p>
				</ModalBody>
				<ModalFooter>
					<Button variant='outline-primary' onClick={() => setShowModal(false)}>
						{strings.collection.profile.deleteForm.cancel}
					</Button>
					<Button variant='outline-danger' onClick={() => deleteUser(user.url)}>
						{strings.collection.profile.deleteForm.confirm}
					</Button>
				</ModalFooter>
			</Modal>
		</>
	);
}
