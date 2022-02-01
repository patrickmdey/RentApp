import { FormGroup, FormLabel, InputGroup, FormControl } from 'react-bootstrap';
import { Path, PathValue, UseFormRegister, Validate, ValidationRule } from 'react-hook-form';

interface ValidationInterface<T> {
	required: string | ValidationRule<boolean>;
	min: ValidationRule<string | number>;
	max: ValidationRule<string | number>;
	minLength: ValidationRule<number>;
	maxLength: ValidationRule<number>;
	pattern: ValidationRule<RegExp>;
	validate: Validate<PathValue<T, Path<T>>> | Record<string, Validate<PathValue<T, Path<T>>>>;
	deps: string[];
}

export default function FormInput<T>(props: {
	register: UseFormRegister<T>;
	label: string;
	name: Path<T>;
	type: string;
	placeholder?: string;
	prependIcon?: JSX.Element | string | null;
	appendIcon?: JSX.Element | null;
	validation?: Partial<ValidationInterface<T>>;
}) {
	const { register, label, name, type, placeholder, prependIcon, appendIcon, validation } = props;
	return (
		<FormGroup>
			<FormLabel>{label}</FormLabel>
			<InputGroup>
				{prependIcon != null && <InputGroup.Text>{prependIcon}</InputGroup.Text>}
				<FormControl type={type} placeholder={placeholder} {...register(name, validation)} />
				{appendIcon != null && <InputGroup.Text>{appendIcon}</InputGroup.Text>}
			</InputGroup>
		</FormGroup>
	);
}
