import { useEffect, useState } from 'react';
import { Col, FormControl, FormGroup, FormLabel, Image, InputGroup, Row } from 'react-bootstrap';
import { UseFormRegister, Path } from 'react-hook-form';
import ImageInput from './ImageInput';
import ImagePreview from './ImagePreview';

export default function MultipleImageInput<T>(props: {
	register: UseFormRegister<T>;
	label: string;
	name: Path<T>;
	hasError: boolean;
	max: number;
	errorMessage: string;
	setValue: Function;
}) {
	const [reader] = useState(new FileReader());
	const { label, setValue, hasError, errorMessage, max } = props;
	const [addInput, setAddInput] = useState(true);
	const [files, setFiles] = useState<File[]>([]);
	const [removedIdx, setRemovedIdx] = useState(-1);
	const [previews, setPreviews] = useState<string[]>([]);
	useEffect(() => setValue(files), [files]);

	useEffect(() => {
		if (files.length == max) setAddInput(false);
		else if (!addInput) setAddInput(true);
	}, [files]);

	useEffect(() => {
		if (removedIdx != -1) {
			setPreviews((prev) => {
				const copy = [...prev];
				copy.splice(removedIdx, 1);
				return copy;
			});
			setRemovedIdx(-1);
			return;
		}
		if (files.length == 0) {
			setPreviews([]);
			return;
		}

		reader.readAsDataURL(files[files.length - 1]);
		reader.onload = () => {
			const r = reader.result;
			if (r != null) setPreviews((prev) => [...prev, r.toString()]);
		};
	}, [files]);

	return (
		<FormGroup>
			{label && <FormLabel>{label}</FormLabel>}
			<InputGroup>
				<Row>
					{previews.map((img, idx) => (
						<Col key={idx}>
							<ImagePreview
								img={img}
								onDelete={() => {
									setFiles((prev) => {
										const copy = [...prev];
										copy.splice(idx, 1);
										return copy;
									});
									setRemovedIdx(idx);
								}}
							/>
						</Col>
					))}
					<Col key={-1}>
						{addInput && <ImageInput onSelect={(file: File) => setFiles((prev) => [...prev, file])} />}
					</Col>
				</Row>
			</InputGroup>
			{hasError && <p className='text-danger fw-light fs-7'>{errorMessage}</p>}
		</FormGroup>
	);
}
