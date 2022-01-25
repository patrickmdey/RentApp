import { BaseApiSlice } from '../baseApiSlice';
import { Category } from './types';

const CategoriesApiSlice = BaseApiSlice.injectEndpoints({
	endpoints: (build) => ({
		findCategory: build.query<Category, URL>({
			query: (url) => url.toString()
		}),

		listCategories: build.query<Category[], void>({
			query: () => 'categories'
		})
	})
});

export const { useListCategoriesQuery: useListCategories, useFindCategoryQuery: useFindCategory } = CategoriesApiSlice;
