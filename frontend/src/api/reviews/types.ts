export interface Review {
    rating: number;
    message: string;
    createdAt: Date;
    url: string;
    articleUrl: string;
    renterUrl: string;
    id: number;
}

export interface ListReviewParameters {
    articleId: number;
    page?: number;
}

export interface CreateReviewParameters {
    rating: number;
    message: string;
    articleId: number;
    renterId: number;
}

export interface UpdateReviewParameters {
    rating: number;
    message: string;
    url: string;
}
