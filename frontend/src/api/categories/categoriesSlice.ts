import { BaseApiSlice } from '../baseApiSlice';
import { Category } from './types';

const CategoriesApiSlice = BaseApiSlice.injectEndpoints({
	endpoints: (build) => ({
		findCategory: build.query<Category, string>({
			query: (url) => url.toString()
		}),

		listCategories: build.query<Category[], void>({
			query: () => 'categories'
		}),

		listCategoriesFromArticle: build.query<Category[], string>({
			query: (url) => url.toString()
		})
	})
});

export const {
	useListCategoriesQuery: useListCategories,
	useFindCategoryQuery: useFindCategory,
	useListCategoriesFromArticleQuery: useListCategoriesFromArticle
} = CategoriesApiSlice;
