import { FormGroup, FormLabel, InputGroup, FormControl, Form } from 'react-bootstrap';
import { UseFormRegister, Path } from 'react-hook-form';

export default function FormSelect<T>(props: {
	register: UseFormRegister<T>;
	label: string;
	name: Path<T>;
	options: [number, string][];
	placeholder?: string;
	prependIcon?: JSX.Element | null;
	appendIcon?: JSX.Element | null;
}) {
	const { register, label, name, options, prependIcon, appendIcon } = props;
	return (
		<FormGroup>
			<FormLabel>{label}</FormLabel>
			<InputGroup>
				{prependIcon != null && <InputGroup.Text>{prependIcon}</InputGroup.Text>}
				<Form.Select {...register(name)}>
					{options.map(([value, message]) => (
						<option key={value} value={value}>
							{message}
						</option>
					))}
				</Form.Select>
				{appendIcon != null && <InputGroup.Text>{appendIcon}</InputGroup.Text>}
			</InputGroup>
		</FormGroup>
	);
}
