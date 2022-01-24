import { Url } from "url";


export interface Review {
    rating: Number,
    message: String,
    createdAt: Date,
    url: Url,
    articleUrl: Url,
    renterUrl: Url
}

export interface ListReviewParameters {
    articleId: Number,
    page: Number | void
}


export interface CreateReviewParameters {
    rating: Number,
    message: String,
    articleId: Number,
    renterId: Number
}

export interface UpdateReviewParameters {
    rating: Number,
    message: String,
    id: Number
}

