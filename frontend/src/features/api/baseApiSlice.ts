// Or from '@reduxjs/toolkit/query' if not using the auto-generated hooks
import { RootState } from '../../store';
import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';

// initialize an empty api service that we'll inject endpoints into later as needed
export const BaseApiSlice = createApi({
	reducerPath: 'api',

	baseQuery: fetchBaseQuery({
		baseUrl: process.env.REACT_APP_BASE_URL || 'api',
		prepareHeaders(headers, { getState }) {
			const { token, user } = (getState() as RootState).auth;

			if (token) headers.set('Bearer token', token);
			else if (user) {
				// TODO: add username and password headers
			}

			return headers;
		}
	}),
	endpoints: () => ({})
});
