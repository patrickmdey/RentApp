import paginatedResponse, { PaginatedData } from '../paginatedResponse';
import { BaseApiSlice } from '../baseApiSlice';
import { Article, ListArticleParameters, CreateArticleParameters, UpdateArticleParameters } from './types';

const ArticlesApiSlice = BaseApiSlice.injectEndpoints({
	endpoints: (build) => ({
		findArticle: build.query<Article, URL>({
			query: (url) => url.toString()
		}),

		listArticles: build.query<PaginatedData<Article[]>, ListArticleParameters>({
			query: ({ page = 1, orderBy = 'HIGHER_RATING' }) => `articles?page=${page}&orderBy=${orderBy}`,
			transformResponse: (response: Article[], meta) => paginatedResponse(response, meta)
		}),

		createArticle: build.mutation<Article, CreateArticleParameters>({
			query: (args) => ({
				url: 'articles',
				method: 'POST',
				body: args
			})
		}),

		updateArticle: build.mutation<void, UpdateArticleParameters>({
			query: ({ url, ...args }) => ({
				url: url.toString(),
				method: 'PUT',
				body: args
			})
		})
	})
});

export const {
	useListArticlesQuery: useListArticles,
	useFindArticleQuery: useFindArticle,
	useCreateArticleMutation: useCreateArticle,
	useUpdateArticleMutation: useUpdateArticle
} = ArticlesApiSlice;
