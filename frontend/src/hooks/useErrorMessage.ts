import { useEffect, useState } from 'react';
import { FieldError } from 'react-hook-form';
import ValidationInterface from '../components/Forms/ValidationInterface';
import { ErrorMessageInterface } from '../i18n/types';

export default function useErrorMessage<T>(
	errorStrings: ErrorMessageInterface | undefined,
	error: FieldError | null | undefined,
	validation: Partial<ValidationInterface<T>> | undefined
): string | null {
	const [message, setMessage] = useState<string | null>(null);

	useEffect(() => {
		if (error == null || errorStrings == null || validation == null) {
			setMessage(null);
			return;
		}

		let msg = errorStrings[error?.type as keyof ErrorMessageInterface];
		if (msg == null) {
			setMessage(null);
			return;
		}

		for (const [key, value] of Object.entries(validation)) msg = msg.replace(`{${key}}`, value.toString());

		setMessage(msg);
		return;
	}, [error, errorStrings, validation]);

	return message;
}
