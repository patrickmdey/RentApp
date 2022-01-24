import { BaseApiSlice } from "../baseApiSlice";
import { Review, ListReviewParameters, CreateReviewParameters, UpdateReviewParameters } from "./types";


const ReviewsApiSlice = BaseApiSlice.injectEndpoints({
    endpoints: (build) => ({

        find: build.query<Review, Number>({
            query: (id) => `reviews/${id}`
        }),

        list: build.query<Review[], ListReviewParameters>({
            query: ({ articleId, page }) => `reviews?fromArticle=${articleId}${page != null ? `&page=${page}` : ""}`
        }),

        create: build.mutation<Review, CreateReviewParameters>({
            query: (args) => ({
                url: 'reviews',
                method: 'POST',
                body: args
            })
        }),

        update: build.mutation<void, Partial<UpdateReviewParameters> & Pick<UpdateReviewParameters, 'id'>>({
            query: ({id, ...args }) => ({
                url: `reviews/${id}`,
                method: 'PUT',
                body: args
            })
        })

    }),
})

export const { useCreateMutation, useFindQuery, useListQuery, useUpdateMutation } = ReviewsApiSlice;