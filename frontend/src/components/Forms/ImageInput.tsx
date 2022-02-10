import React, { useRef } from 'react';
import { FormGroup, FormLabel, InputGroup, FormControl, Form } from 'react-bootstrap';
import { UseFormRegister, FieldError, Path } from 'react-hook-form';
import ValidationInterface from './ValidationInterface';

export default function ImageInput<T>(props: {
	register: UseFormRegister<T>;
	name: Path<T>;
	label?: string;
	onSelect: Function;
}) {
	const { register, label, name, onSelect } = props;
	const inputRef = useRef<HTMLInputElement>(null);

	return (
		<FormGroup>
			{label && <FormLabel>{label}</FormLabel>}
			<InputGroup>
				<FormControl
					type='file'
					{...register(name)}
					ref={inputRef}
					onChange={(_) => {
						console.log(inputRef?.current?.files);
						if (inputRef?.current?.files && inputRef?.current?.files.length)
							onSelect(inputRef?.current?.files[0]);
					}}
				/>
			</InputGroup>
		</FormGroup>
	);
}
