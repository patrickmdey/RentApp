import { useEffect, useState } from 'react';
import { FormGroup, FormLabel, InputGroup, FormControl } from 'react-bootstrap';
import { FieldError, Path, UseFormRegister } from 'react-hook-form';
import useErrorMessage from '../../hooks/useErrorMessage';
import { ErrorMessageInterface } from '../../i18n/types';
import ValidationInterface from './ValidationInterface';

export default function FormInput<T>(props: {
	register: UseFormRegister<T>;
	label: string;
	name: Path<T>;
	type: string;
	disabled?: boolean;
	value?: string;
	error?: FieldError | null;
	errorMessage?: ErrorMessageInterface;
	placeholder?: string;
	prependIcon?: JSX.Element | string | null;
	appendIcon?: JSX.Element | null;
	validation?: Partial<ValidationInterface<T>>;
}) {
	const {
		register,
		label,
		name,
		value,
		error,
		errorMessage,
		type,
		disabled,
		placeholder,
		prependIcon,
		appendIcon,
		validation
	} = props;

	const errorDesc = useErrorMessage(errorMessage, error, validation);

	return (
		<FormGroup>
			<FormLabel>{label}</FormLabel>
			<InputGroup>
				{prependIcon != null && <InputGroup.Text>{prependIcon}</InputGroup.Text>}
				<FormControl
					type={type}
					{...(type == 'number' ? { step: '0.01' } : {})}
					defaultValue={value}
					placeholder={placeholder}
					{...register(name, validation)}
					isInvalid={error != null}
					disabled={disabled}
				/>
				{appendIcon != null && <InputGroup.Text>{appendIcon}</InputGroup.Text>}
				{errorDesc && <FormControl.Feedback type='invalid'>{errorDesc}</FormControl.Feedback>}
			</InputGroup>
		</FormGroup>
	);
}
