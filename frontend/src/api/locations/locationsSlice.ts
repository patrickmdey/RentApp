import { BaseApiSlice } from '../baseApiSlice';
import { Location } from './types';

const LocationsApiSlice = BaseApiSlice.injectEndpoints({
	endpoints: (build) => ({
		findLocation: build.query<Location, string>({
			query: (url) => url.toString()
		}),

		listLocations: build.query<Location[], void>({
			query: () => 'locations'
		}),

		listUsedLocations: build.query<Location[], void>({
			query: () => 'locations?used=true'
		})

	})
});

export const { useListLocationsQuery: useListLocations, useFindLocationQuery: useFindLocation,
	useListUsedLocationsQuery: useListUsedLocations } = LocationsApiSlice;
