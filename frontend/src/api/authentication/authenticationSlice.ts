import { BaseApiSlice } from '../baseApiSlice';
import { Buffer } from 'buffer';

interface Credentials {
	email: string;
	password: string;
	callback: Function;
}

const AuthApiSlice = BaseApiSlice.injectEndpoints({
	endpoints: (build) => ({
		login: build.mutation<void, Credentials>({
			query: ({ email, password }) => {
				const credentials = Buffer.from(`${email}:${password}`).toString('base64');

				return {
					url: '/locations',
					method: 'GET',
					headers: [['Authorization', 'Basic ' + credentials]]
				};
			},
			transformResponse: (_, meta, { callback }) => {
				const auth = meta?.response?.headers.get('Authorization');
				if (auth == null) return callback(null);

				const token = auth.split(' ')[1];
				if (token == null) return callback(null);

				return callback(token);
			}
		})
	})
});

export const { useLoginMutation: useLogin } = AuthApiSlice;
