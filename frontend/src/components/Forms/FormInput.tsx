import { FormGroup, FormLabel, InputGroup, FormControl } from 'react-bootstrap';
import { Path, UseFormRegister } from 'react-hook-form';

export default function FormInput<T>(props: {
	register: UseFormRegister<T>;
	label: string;
	name: Path<T>;
	type: string;
	placeholder?: string;
	prependIcon?: JSX.Element | null;
	appendIcon?: JSX.Element | null;
}) {
	const { register, label, name, type, placeholder, prependIcon, appendIcon } = props;
	return (
		<FormGroup>
			<FormLabel>{label}</FormLabel>
			<InputGroup>
				{prependIcon != null && <InputGroup.Text>{prependIcon}</InputGroup.Text>}
				<FormControl type={type} placeholder={placeholder} {...register(name)} />
				{appendIcon != null && <InputGroup.Text>{appendIcon}</InputGroup.Text>}
			</InputGroup>
		</FormGroup>
	);
}
