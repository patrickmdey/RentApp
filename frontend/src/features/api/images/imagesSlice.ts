import { BaseApiSlice } from '../baseApiSlice';
import { Image } from './types';

const ImagesApiSlice = BaseApiSlice.injectEndpoints({
	endpoints: (build) => ({
		listImages: build.query<Image[], URL>({
			query: (url) => url.toString()
		})
	})
});

export const { useListImagesQuery: useListImages } = ImagesApiSlice;
