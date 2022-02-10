import { useRef } from 'react';
import { Button, FormGroup, FormLabel, InputGroup } from 'react-bootstrap';

export default function ImageInput<T>(props: { label?: string; onSelect: Function }) {
	const { label, onSelect } = props;
	const inputRef = useRef<HTMLInputElement>(null);

	return (
		<FormGroup>
			{label && <FormLabel>{label}</FormLabel>}
			<InputGroup>
				<Button
					style={{ width: '40px', height: '40px', borderRadius: 0 }}
					variant='outline-secondary'
					onClick={() => inputRef.current?.click()}
				>
					+
				</Button>
				<input
					type='file'
					className='d-none'
					ref={inputRef}
					onChange={(_) => {
						if (inputRef?.current?.files && inputRef?.current?.files.length) {
							onSelect(inputRef.current.files[0]);
							inputRef.current.files = null;
							inputRef.current.value = '';
						}
					}}
				/>
			</InputGroup>
		</FormGroup>
	);
}
