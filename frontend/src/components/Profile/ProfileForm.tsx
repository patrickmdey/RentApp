import { useEffect } from 'react';
import { Button, Col, Form, Row, Stack } from 'react-bootstrap';
import { useForm } from 'react-hook-form';
import { useListLocations } from '../../features/api/locations/locationsSlice';
import { useUpdateUser } from '../../features/api/users/usersSlice';
import { strings } from '../../i18n/i18n';
import FormInput from '../Forms/FormInput';
import FormSelect from '../Forms/FormSelect';

interface EditUserForm {
	firstName: string;
	lastName: string;
	location: string;
	ignored: string;
}

export default function ProfileForm(props: {
	url: string;
	firstName: string;
	lastName: string;
	location: string;
	email: string;
	onDone: Function;
	disabled: boolean;
}) {
	const { url, email, firstName, lastName, location, onDone, disabled } = props;
	const [modifyUser, result] = useUpdateUser();
	useEffect(() => {
		if (result.isSuccess) onDone();
	}, [result]);

	const {
		register,
		handleSubmit,
		formState: { errors }
	} = useForm<EditUserForm>({
		defaultValues: {
			firstName: firstName,
			lastName: lastName,
			location: location
		}
	});

	const { data: locations } = useListLocations();

	function onSubmit(data: EditUserForm) {
		console.log(data);
		modifyUser({ url: url, ...data });
	}

	return (
		<Form onSubmit={handleSubmit(onSubmit)}>
			{locations && (
				<>
					<Row sm={1} md={2} className='g-3'>
						<Col>
							<FormInput
								register={register}
								type='text'
								name='firstName'
								disabled={disabled}
								label={strings.collection.register.firstName}
							/>
						</Col>
						<Col>
							<FormInput
								register={register}
								type='text'
								name='lastName'
								disabled={disabled}
								label={strings.collection.register.lastName}
							/>
						</Col>
						{disabled && (
							<Col>
								<FormInput
									register={register}
									type='text'
									name='ignored'
									disabled={disabled}
									value={email}
									label={strings.collection.register.email}
								/>
							</Col>
						)}
						<Col>
							<FormSelect
								register={register}
								name='location'
								value={location}
								disabled={disabled}
								options={locations ? locations.map(({ id, name }) => [id, name]) : []}
								label={strings.collection.register.location}
							/>
						</Col>
					</Row>
					{!disabled && (
						<Stack direction='horizontal'>
							<Button className='ms-auto' variant='outline-danger' onClick={(_) => onDone()}>
								Cancelar
							</Button>
							<Button type='submit' className='ms-2' variant='primary'>
								Guardar
							</Button>
						</Stack>
					)}
				</>
			)}
		</Form>
	);
}
