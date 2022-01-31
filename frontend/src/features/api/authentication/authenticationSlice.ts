import { BaseApiSlice } from '../baseApiSlice';
import { Buffer } from 'buffer';
import { setCredentials } from '../../auth/authSlice';

interface Credentials {
	email: string;
	password: string;
}

const AuthApiSlice = BaseApiSlice.injectEndpoints({
	endpoints: (build) => ({
		login: build.mutation<string | null, Credentials>({
			query: ({ email, password }) => {
				const credentials = Buffer.from(`${email}:${password}`).toString('base64');
				console.log(credentials);

				return {
					url: '/locations',
					method: 'GET',
					headers: [['Authorization', 'Basic ' + credentials]]
				};
			},
			transformResponse: (_, meta) => {
				const auth = meta?.response?.headers.get('Authorization');
				if (auth == null) return null;

				const token = auth.split(' ')[1];
				if (token == null) return null;

				return token;
			}
		})
	})
});

export const { useLoginMutation: useLogin } = AuthApiSlice;
