import { BaseApiSlice } from '../baseApiSlice';
import { Location } from './types';

const LocationsApiSlice = BaseApiSlice.injectEndpoints({
	endpoints: (build) => ({
		findLocation: build.query<Location, URL>({
			query: (url) => url.toString()
		}),

		listLocations: build.query<Location[], void>({
			query: () => 'locations'
		})
	})
});

export const { useListLocationsQuery: useListLocations, useFindLocationQuery: useFindLocation } = LocationsApiSlice;
