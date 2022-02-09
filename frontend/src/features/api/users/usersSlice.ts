import { BaseApiSlice } from '../baseApiSlice';
import { User, CreateUserParameters, UpdateUserParameters, UpdatePasswordParameters } from './types';

const UsersApiSlice = BaseApiSlice.injectEndpoints({
	endpoints: (build) => ({
		findUser: build.query<User, URL>({
			query: (url) => url.toString()
		}),

		createUser: build.mutation<User, CreateUserParameters>({
			query: (jsonUser) => {
				let data = new FormData();
				for (let [key, val] of Object.entries(jsonUser)) data.append(key, val);

				return {
					url: 'users',
					method: 'POST',
					body: data
				};
			}
		}),

		updateUser: build.mutation<void, UpdateUserParameters>({
			query: ({ url, ...args }) => {
				return {
					url: url.toString(),
					method: 'PUT',
					body: args
				};
			}
		}),

		updatePassword: build.mutation<void, UpdatePasswordParameters>({
			query: ({ url, ...args }) => {
				return {
					url: url.toString(),
					method: 'PUT',
					body: args
				};
			}
		}),

		deleteUser: build.mutation<void, URL>({
			query: (url) => {
				return {
					url: url.toString(),
					method: 'DELETE'
				};
			}
		})
	})
});

export const {
	useFindUserQuery: useFindUser,
	useCreateUserMutation: useCreateUser,
	useDeleteUserMutation: useDeleteUser,
	useUpdateUserMutation: useUpdateUser,
	useUpdatePasswordMutation: useUpdatePassword
} = UsersApiSlice;
