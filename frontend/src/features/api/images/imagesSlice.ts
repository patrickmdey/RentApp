import { BaseApiSlice } from '../baseApiSlice';
import { Image } from './types';

const ImagesApiSlice = BaseApiSlice.injectEndpoints({
	endpoints: (build) => ({
		list: build.query<Image[], URL>({
			query: (url) => url.toString()
		})
	})
});

export const { useListQuery: useListImages } = ImagesApiSlice;
