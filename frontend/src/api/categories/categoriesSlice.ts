import { BaseApiSlice } from '../baseApiSlice';
import { Category } from './types';

const CategoriesApiSlice = BaseApiSlice.injectEndpoints({
	endpoints: (build) => ({
		findCategory: build.query<Category, string>({
			query: (url) => url.toString()
		}),

		listCategories: build.query<Category[], void>({
			query: () => 'categories',
			providesTags: ['Category']
		}),

		listCategoriesFromArticle: build.query<Category[], string>({
			query: (url) => url.toString(),
			providesTags: (_, _e, args) => {
				const parts = args.split('fromArticle=');
				return [{ type: 'ArticleCategory', id: parts[parts.length - 1] }];
			}
		})
	})
});

export const {
	useListCategoriesQuery: useListCategories,
	useFindCategoryQuery: useFindCategory,
	useListCategoriesFromArticleQuery: useListCategoriesFromArticle
} = CategoriesApiSlice;
