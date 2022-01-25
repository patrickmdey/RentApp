import { BaseApiSlice } from '../baseApiSlice';
import { Review, ListReviewParameters, CreateReviewParameters, UpdateReviewParameters } from './types';

const ReviewsApiSlice = BaseApiSlice.injectEndpoints({
	endpoints: (build) => ({
		findReview: build.query<Review, URL>({
			query: (url) => url.toString()
		}),

		listReviews: build.query<Review[], ListReviewParameters>({
			query: ({ articleId, page }) => `reviews?fromArticle=${articleId}${page != null ? `&page=${page}` : ''}`
		}),

		createReview: build.mutation<Review, CreateReviewParameters>({
			query: (args) => ({
				url: 'reviews',
				method: 'POST',
				body: args
			})
		}),

		updateReview: build.mutation<void, UpdateReviewParameters>({
			query: ({ url, ...args }) => ({
				url: url.toString(),
				method: 'PUT',
				body: args
			})
		})
	})
});

export const {
	useListReviewsQuery: useListReviews,
	useFindReviewQuery: useFindReview,
	useCreateReviewMutation: useCreateReview,
	useUpdateReviewMutation: useUpdateReview
} = ReviewsApiSlice;
