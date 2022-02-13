import { FormGroup, FormLabel, InputGroup, Form } from 'react-bootstrap';
import { UseFormRegister, Path } from 'react-hook-form';
import ValidationInterface from './ValidationInterface';

export default function FormCheckbox<T>(props: {
	register: UseFormRegister<T>;
	label: string;
	name: Path<T>;
	validation?: Partial<ValidationInterface<T>>;
	value?: number;
	checked?: boolean;
}) {
	const { register, label, name, value, validation, checked } = props;
	return (
		<FormGroup>
			<InputGroup>
				<Form.Check label={label} {...register(name, validation)} value={value} defaultChecked={checked} />
			</InputGroup>
		</FormGroup>
	);
}
