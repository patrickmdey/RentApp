import { Button, Card, Form, Row, Col } from 'react-bootstrap';
import { Path, useForm } from 'react-hook-form';
import { strings } from '../i18n/i18n';
import { BsEyeSlash } from 'react-icons/bs';
import { useCreateUser } from '../features/api/users/usersSlice';
import FormInput from './Forms/FormInput';
import FormSelect from './Forms/FormSelect';
import FormCheckbox from './Forms/FormCheckbox';
import { useListLocations } from '../features/api/locations/locationsSlice';

interface RegisterForm {
	firstName: string;
	lastName: string;
	email: string;
	location: number;
	password: string;
	confirmPassword: string;
	isOwner: boolean;
	image: FileList;
}

export function Register() {
	const { register, handleSubmit } = useForm<RegisterForm>({
		defaultValues: {
			firstName: '',
			lastName: '',
			email: '',
			location: 0,
			password: '',
			confirmPassword: '',
			isOwner: false,
			image: undefined
		}
	});
	const { data: locations } = useListLocations();
	const [createUser, result] = useCreateUser();
	console.log(result);

	function onSubmit(data: RegisterForm) {
		createUser({ ...data, image: data.image[0] });
	}

	return (
		<Card className='shadow card-style create-card mx-3'>
			<Card.Body className='form-container'>
				<Form onSubmit={handleSubmit(onSubmit)}>
					<Row>
						<h3 className='fw-bold my-1'>{strings.collection.register.title}</h3>
					</Row>
					<hr />
					<Row sm={1} md={2} className='g-3'>
						<Col>
							<FormInput
								register={register}
								label={strings.collection.register.firstName}
								name='firstName'
								type='text'
								placeholder={strings.collection.register.firstNamePlaceholder}
							/>
						</Col>
						<Col>
							<FormInput
								register={register}
								label={strings.collection.register.lastName}
								name='lastName'
								type='text'
								placeholder={strings.collection.register.lastNamePlaceholder}
							/>
						</Col>
						<Col md={12}>
							<FormInput
								register={register}
								label={strings.collection.register.email}
								name='email'
								type='text'
								placeholder={strings.collection.register.emailPlaceholder}
							/>
						</Col>
						<Col>
							<FormInput
								register={register}
								label={strings.collection.register.password}
								name='password'
								type='password'
								placeholder={strings.collection.register.passwordPlaceholder}
								appendIcon={<BsEyeSlash />}
							/>
						</Col>
						<Col>
							<FormInput
								register={register}
								label={strings.collection.register.password}
								name='confirmPassword'
								type='password'
								placeholder={strings.collection.register.passwordPlaceholder}
								appendIcon={<BsEyeSlash />}
							/>
						</Col>
						<Col>
							<FormSelect
								register={register}
								name='location'
								label={strings.collection.register.location}
								options={locations ? locations.map(({ id, name }) => [id, name]) : []}
							/>
						</Col>
						<Col>
							<FormInput
								register={register}
								label={strings.collection.register.image}
								name='image'
								type='file'
								placeholder={strings.collection.register.image}
							/>
						</Col>
						<Col md={12}>
							<FormCheckbox
								register={register}
								name='isOwner'
								label={strings.collection.register.isRenter}
							/>
						</Col>
						<Col md={12}>
							<Button type='submit' className='rounded btn-block bg-color-action btn-dark'>
								{strings.collection.register.confirmButton}
							</Button>
						</Col>
					</Row>
				</Form>
			</Card.Body>
		</Card>
	);
}
