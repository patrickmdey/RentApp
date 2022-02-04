import { Button, Card, Form, Row, Stack } from 'react-bootstrap';
import { strings } from '../i18n/i18n';
import { useForm } from 'react-hook-form';
import { useState } from 'react';
import { LinkContainer } from 'react-router-bootstrap';
import { useLogin } from '../features/api/authentication/authenticationSlice';
import FormInput from './Forms/FormInput';
import FormCheckbox from './Forms/FormCheckbox';
import { useAppDispatch } from '../hooks';
import { setCredentials } from '../features/auth/authSlice';

interface LogInForm {
	email: string;
	password: string;
	rememberMe: boolean;
}

export default function LogInComponent() {
	const [state, setState] = useState({ showPassword: false });
	const [login, result] = useLogin();

	const dispatch = useAppDispatch();

	if (result && result.data) {
		dispatch(setCredentials({ token: result.data }));
	}

	function updatePasswordType() {
		state.showPassword = !state.showPassword;
	}

	const {
		register,
		handleSubmit,
		formState: { errors }
	} = useForm<LogInForm>({
		defaultValues: { email: '', password: '' }
	});

	function onSubmit(data: LogInForm) {
		const d = login(data);
	}

	return (
		<Card className='shadow card-style create-card mx-3'>
			<Card.Body className='form-container'>
				<Form onSubmit={handleSubmit(onSubmit)}>
					<h3 className='fw-bold my-1'>{strings.collection.login.title}</h3>
					<hr />
					<Row xs={1} className='g-2'>
						<FormInput
							register={register}
							type='email'
							name='email'
							label={strings.collection.login.email}
							placeholder={strings.collection.login.emailPlaceholder}
							error={errors.email}
							errorMessage={strings.collection.login.errors.email}
							validation={{ required: true, minLength: 3, maxLength: 320 }}
						/>
						<FormInput
							register={register}
							type='password'
							name='password'
							label={strings.collection.login.password}
							placeholder={strings.collection.login.passwordPlaceholder}
							error={errors.password}
							errorMessage={strings.collection.login.errors.password}
							validation={{ required: true, minLength: 8, maxLength: 20 }}
						/>
						<FormCheckbox
							register={register}
							label={strings.collection.login.rememberMe}
							name='rememberMe'
						/>
						<Stack direction='vertical'>
							<Button type='submit' className='btn-block bg-color-action btn-dark mt-3 mb-2'>
								{strings.collection.login.loginButton}
							</Button>
							<LinkContainer to='/register'>
								<button type='button' className='color-action btn'>
									{strings.collection.login.signupButton}
								</button>
							</LinkContainer>
						</Stack>
					</Row>
				</Form>
			</Card.Body>
		</Card>
	);
}
