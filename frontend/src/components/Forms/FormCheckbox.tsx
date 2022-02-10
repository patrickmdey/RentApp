import { FormGroup, FormLabel, InputGroup, Form } from 'react-bootstrap';
import { UseFormRegister, Path } from 'react-hook-form';

export default function FormCheckbox<T>(props: {
	register: UseFormRegister<T>;
	label: string;
	name: Path<T>;
	value?: number;
}) {
	const { register, label, name, value } = props;
	return (
		<FormGroup>
			<InputGroup>
				<Form.Check label={label} {...register(name)} value={value} />
			</InputGroup>
		</FormGroup>
	);
}
