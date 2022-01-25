import { BaseApiSlice } from '../baseApiSlice';
import { Category } from './types';

const CategoriesApiSlice = BaseApiSlice.injectEndpoints({
	endpoints: (build) => ({
		find: build.query<Category, URL>({
			query: (url) => url.toString()
		}),

		list: build.query<Category[], void>({
			query: () => 'categories'
		})
	})
});

export const { useListQuery: useListCategories, useFindQuery: useFindCategory } = CategoriesApiSlice;
