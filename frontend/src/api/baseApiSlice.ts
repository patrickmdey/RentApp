import { RootState } from '../store';
import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';

export const BaseApiSlice = createApi({
	reducerPath: 'api',
	tagTypes: ['Article', 'ArticleCategory'],
	baseQuery: fetchBaseQuery({
		baseUrl: process.env.REACT_APP_BASE_URL || 'api',
		prepareHeaders(headers, { getState }) {
			const state = getState() as RootState;
			// console.log('token', state);
			if (state && state.auth.token) headers.set('Authorization', 'Bearer ' + state.auth.token);

			return headers;
		}
	}),
	endpoints: () => ({})
});
