// Or from '@reduxjs/toolkit/query' if not using the auto-generated hooks
import { RootState } from '../../store';
import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';

// initialize an empty api service that we'll inject endpoints into later as needed
export const BaseApiSlice = createApi({
	reducerPath: 'api',

	baseQuery: fetchBaseQuery({
		baseUrl: process.env.REACT_APP_BASE_URL || 'api',
		prepareHeaders(headers, { getState }) {
			const state = getState() as RootState;
			console.log('token', state);
			if (state && state.auth.token) headers.set('Authorization', 'Bearer ' + state.auth.token);

			return headers;
		}
	}),
	endpoints: () => ({})
});
