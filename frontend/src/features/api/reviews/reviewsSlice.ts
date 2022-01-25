import { BaseApiSlice } from '../baseApiSlice';
import { Review, ListReviewParameters, CreateReviewParameters, UpdateReviewParameters } from './types';

const ReviewsApiSlice = BaseApiSlice.injectEndpoints({
	endpoints: (build) => ({
		find: build.query<Review, URL>({
			query: (url) => url.toString()
		}),

		list: build.query<Review[], ListReviewParameters>({
			query: ({ articleId, page }) => `reviews?fromArticle=${articleId}${page != null ? `&page=${page}` : ''}`
		}),

		create: build.mutation<Review, CreateReviewParameters>({
			query: (args) => ({
				url: 'reviews',
				method: 'POST',
				body: args
			})
		}),

		update: build.mutation<void, UpdateReviewParameters>({
			query: ({ url, ...args }) => ({
				url: url.toString(),
				method: 'PUT',
				body: args
			})
		})
	})
});

export const {
	useListQuery: useListReviews,
	useFindQuery: useFindReview,
	useCreateMutation: useCreateReview,
	useUpdateMutation: useUpdateReview
} = ReviewsApiSlice;
