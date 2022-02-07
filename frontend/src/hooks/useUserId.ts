import { useAppSelector } from '../hooks';
import { Buffer } from 'buffer';

export default function useUserId() {
	const token = useAppSelector((state) => state.auth.token);
	if (token == null) return null;

	const info = token.split('.');
	if (info.length !== 3) return null;

	const json = JSON.parse(Buffer.from(info[1], 'base64').toString());
	if (json == null || json.sub == null) return null;

	const parts = json.sub.split(',');
	if (parts.length !== 2) return null;

	return parseInt(parts[1]);
}
