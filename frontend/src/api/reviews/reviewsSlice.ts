import {BaseApiSlice} from '../baseApiSlice';
import {Review, CreateReviewParameters, UpdateReviewParameters, ListReviewParameters} from './types';
import paginatedResponse, {PaginatedData} from "../paginatedResponse";

const ReviewsApiSlice = BaseApiSlice.injectEndpoints({
    endpoints: (build) => ({
        findReview: build.query<Review, string>({
            query: (url) => url.toString(),
            providesTags: (_, _e, url) => {
                const parts = url.split('/');
                return [{type: 'Review', id: parts[parts.length - 1]}];
            }
        }),

        listReviews: build.query<PaginatedData<Review[]>, ListReviewParameters>({
            query: ({articleId, page}) => `reviews?fromArticle=${articleId}&page=${page}`,
            transformResponse: (response: Review[], meta) => paginatedResponse(response, meta),
            providesTags: (result) =>
                result && result.data ?
                    [
                        ...result.data.map(({id}) => ({type: 'Review' as const, id: id})),
                        {type: 'Review', id: 'PARTIAL-LIST'}
                    ]
                    : [{type: 'Review', id: 'PARTIAL-LIST'}]
        }),

        createReview: build.mutation<Review, CreateReviewParameters>({
            query: (args) => ({
                url: 'reviews',
                method: 'POST',
                body: args
            })
        }),

        updateReview: build.mutation<void, UpdateReviewParameters>({
            query: ({url, ...args}) => ({
                url: url.toString(),
                method: 'PUT',
                body: args
            }),
            invalidatesTags: (_, _e, args) => {
                const parts = args.url.split('/');
                return [{type: 'Review', id: parts[parts.length - 1]}];
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
