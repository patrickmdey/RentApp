import { BaseApiSlice } from '../baseApiSlice';
import { Article, ListArticleParameters, CreateArticleParameters, UpdateArticleParameters } from './types';

const ArticlesApiSlice = BaseApiSlice.injectEndpoints({
	endpoints: (build) => ({
		find: build.query<Article, URL>({
			query: (url) => url.toString()
		}),

		list: build.query<Article[], ListArticleParameters>({
			query: ({ url }) => (url == null ? 'articles' : url.toString())
		}),

		create: build.mutation<Article, CreateArticleParameters>({
			query: (args) => ({
				url: 'articles',
				method: 'POST',
				body: args
			})
		}),

		update: build.mutation<void, UpdateArticleParameters>({
			query: ({ url, ...args }) => ({
				url: url.toString(),
				method: 'PUT',
				body: args
			})
		})
	})
});

export const {
	useListQuery: useListArticles,
	useFindQuery: useFindArticle,
	useCreateMutation: useCreateArticle,
	useUpdateMutation: useUpdateArticlex
} = ArticlesApiSlice;
