import { BaseApiSlice } from '../baseApiSlice';
import { Image } from './types';

const ImagesApiSlice = BaseApiSlice.injectEndpoints({
	endpoints: (build) => ({
		listImages: build.query<Image[], string>({
			query: (url) => url.toString()
		})
	})
});

export const { useListImagesQuery: useListImages } = ImagesApiSlice;
