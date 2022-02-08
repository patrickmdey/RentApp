export interface Review {
    rating: number;
    message: string;
    createdAt: Date;
    url: URL;
    articleUrl: URL;
    renterUrl: URL;
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
    renterId: number; // TODO: esto seria el token de auth en realidad
}

export interface UpdateReviewParameters {
    rating: number;
    message: string;
    url: URL;
}
