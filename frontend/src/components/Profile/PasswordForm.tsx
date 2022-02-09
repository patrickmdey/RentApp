import { useEffect, useState } from 'react';
import { Button, Col, Form, Row, Stack } from 'react-bootstrap';
import { useForm } from 'react-hook-form';
import { User } from '../../features/api/users/types';
import { useUpdatePassword } from '../../features/api/users/usersSlice';
import { strings } from '../../i18n/i18n';
import FormInput from '../Forms/FormInput';

interface ModifyPasswordData {
	password: string;
	confirmPassword: string;
}

export default function PasswordForm(props: { user: User; onDone: Function }) {
	const { onDone, user } = props;

	const {
		register,
		handleSubmit,
		formState: { errors }
	} = useForm<ModifyPasswordData>();

	const [passwordsMatch, setPasswordsMatch] = useState(true);

	const [modifyPassword, result] = useUpdatePassword();

	useEffect(() => {
		if (result.isSuccess) onDone();
	}, [result]);

	function onSubmit(data: ModifyPasswordData) {
		if (data.password != data.confirmPassword) return setPasswordsMatch(false);
		modifyPassword({ ...data, url: new URL('password', user.url + '/') });
	}

	return (
		<Form onSubmit={handleSubmit(onSubmit)}>
			<Row xs={1} sm={2} className='g-3'>
				<Col>
					<FormInput
						register={register}
						type='password'
						name='password'
						label='Password'
						validation={{ required: true, minLength: 8, maxLength: 16 }}
						error={errors.password}
						errorMessage={strings.collection.login.errors.password}
					/>
				</Col>
				<Col>
					<FormInput
						register={register}
						type='password'
						name='confirmPassword'
						label='Confirm Password'
						validation={{ required: true, minLength: 8, maxLength: 16 }}
						error={errors.confirmPassword}
						errorMessage={strings.collection.login.errors.password}
					/>
				</Col>
				{!passwordsMatch && <p className='text-danger'>Passwords must match</p>}
			</Row>
			<Stack direction='horizontal' className='mt-3'>
				<Button onClick={(e) => onDone()} variant='outline-danger' className='ms-auto'>
					Cancelar
				</Button>
				<Button type='submit' className='ms-2'>
					Guardar
				</Button>
			</Stack>
		</Form>
	);
}
