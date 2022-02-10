import { useEffect, useState } from 'react';
import { FormGroup, FormLabel, InputGroup } from 'react-bootstrap';
import { UseFormRegister, Path } from 'react-hook-form';
import ImageInput from './ImageInput';

export default function MultipleImageInput<T>(props: {
	register: UseFormRegister<T>;
	label: string;
	name: Path<T>;
	setValue: Function;
}) {
	const { register, label, name, setValue } = props;

	const [files, setFiles] = useState<File[]>([]);
	useEffect(() => setValue(files), [files]);

	return (
		<FormGroup>
			{label && <FormLabel>{label}</FormLabel>}
			<InputGroup>
				<ImageInput
					register={register}
					name={name}
					onSelect={(file: File) => setFiles((prev) => [...prev, file])}
				/>
			</InputGroup>
		</FormGroup>
	);
}
