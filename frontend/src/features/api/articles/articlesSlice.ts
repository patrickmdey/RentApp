import paginatedResponse, { PaginatedData } from '../paginatedResponse';
import { BaseApiSlice } from '../baseApiSlice';
import { Article, ListArticleParameters, CreateArticleParameters, UpdateArticleParameters } from './types';

const ArticlesApiSlice = BaseApiSlice.injectEndpoints({
	endpoints: (build) => ({
		findArticle: build.query<Article, string>({
			query: (url) => url.toString()
		}),

		listArticles: build.query<PaginatedData<Article[]>, ListArticleParameters>({
			query: (params) => `articles?${new URLSearchParams(Object.entries(params)).toString()}`,
			transformResponse: (response: Article[], meta) => paginatedResponse(response, meta)
		}),

		listRelatedArticles: build.query<Article[], string>({
			query: (params) => `articles/${params}/related`
		}),

		createArticle: build.mutation<string | null, CreateArticleParameters>({
			query: (jsonArticle) => {
				let data = new FormData();
				for (let [key, val] of Object.entries(jsonArticle)) {
					if (Array.isArray(val)) for (let i = 0; i < val.length; i++) data.append(key, val[i]);
					else data.append(key, val);
				}
				return {
					url: 'articles',
					method: 'POST',
					body: data
				};
			},
			transformResponse: (_, meta) => {
				const headers = meta?.response?.headers.get('Location')?.split('/');
				if (headers == null) return null;
				return headers[headers.length - 1];
			}
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
	useListRelatedArticlesQuery: useListRelatedArticles,
	useFindArticleQuery: useFindArticle,
	useCreateArticleMutation: useCreateArticle,
	useUpdateArticleMutation: useUpdateArticle
} = ArticlesApiSlice;
