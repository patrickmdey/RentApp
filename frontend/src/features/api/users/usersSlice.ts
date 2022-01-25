import { BaseApiSlice } from '../baseApiSlice';
import { User, CreateUserParameters, UpdateUserParameters } from './types';

const UsersApiSlice = BaseApiSlice.injectEndpoints({
	endpoints: (build) => ({
		find: build.query<User, URL>({
			query: (url) => url.toString()
		}),

		createUser: build.mutation<User, CreateUserParameters>({
			query: ({ imageFormData: data, ...args }) => {
				for (let [key, val] of Object.entries(args)) data.append(key, val);
				console.log(data);
				return {
					url: 'users',
					method: 'POST',
					headers: [['Content-Type', 'multipart/form-data']],
					body: data
				};
			}
		}),

		update: build.mutation<void, UpdateUserParameters>({
			query: ({ url, ...args }) => {
				return {
					url: url.toString(),
					method: 'PUT',
					body: args
				};
			}
		})
	})
});

export const {
	useFindQuery: useFindUser,
	useCreateUserMutation: useCreateUser,
	useUpdateMutation: useUpdateUser
} = UsersApiSlice;
