import { BaseApiSlice } from '../baseApiSlice';
import { Location } from './types';

const LocationsApiSlice = BaseApiSlice.injectEndpoints({
	endpoints: (build) => ({
		find: build.query<Location, URL>({
			query: (url) => url.toString()
		}),

		list: build.query<Location[], void>({
			query: () => 'locations'
		})
	})
});

export const { useListQuery: useListLocations, useFindQuery: useFindLocation } = LocationsApiSlice;
