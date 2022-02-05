import { useCallback, useEffect, useState } from 'react';

export default function useLocalStorage(key: string, defaultValue: string) {
	const [value, setValue] = useState(() => {
		const jsonValue = window.localStorage.getItem(key);
		if (jsonValue != null) return JSON.parse(jsonValue);

		return defaultValue;
	});

	useEffect(() => {
		if (value === undefined) return window.localStorage.removeItem(key);

		window.localStorage.setItem(key, JSON.stringify(value));
	}, [key, value]);

	const remove = useCallback(() => {
		setValue(undefined);
	}, []);

	return [value, setValue, remove];
}
