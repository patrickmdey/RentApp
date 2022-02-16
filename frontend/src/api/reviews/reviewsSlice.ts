import { BaseApiSlice } from '../baseApiSlice';
import { Review, CreateReviewParameters, UpdateReviewParameters } from './types';

const ReviewsApiSlice = BaseApiSlice.injectEndpoints({
	endpoints: (build) => ({
		findReview: build.query<Review, string>({
			query: (url) => url.toString(),
			providesTags: (_, _e, url) => {
				const parts = url.split('/');
				return [{ type: 'Review', id: parts[parts.length - 1] }];
			}
		}),

		listReviews: build.query<Review[], string>({
			query: (url) => url.toString(),
			providesTags: (result) =>
				result ? [...result.map((r) => ({ type: 'Review' as const, id: r.id }))] : ['Review']
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
			}),
			invalidatesTags: (_, _e, args) => {
				const parts = args.url.split('/');
				return [{ type: 'Review', id: parts[parts.length - 1] }];
			}
		})
	})
});

export const {
	useListReviewsQuery: useListReviews,
	useFindReviewQuery: useFindReview,
	useCreateReviewMutation: useCreateReview,
	useUpdateReviewMutation: useUpdateReview
} = ReviewsApiSlice;
