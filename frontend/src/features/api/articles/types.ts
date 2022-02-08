export interface Article {
    title: string;
    description: string;
    pricePerDay: number;
    timesRented: number;
    timesReviewed: number;
    rating: number;
    url: URL;
    ownerUrl: URL;
    imagesUrl: URL;
    categoriesUrl: URL;
    reviewsUrl: URL;
    id: number;
}

export interface ListArticleParameters {
    page?: number;
    orderBy?: string;
}

export interface CreateArticleParameters {
    title: string;
    description: string;
    pricePerDay: number;
    rating: number;
    categories: number[];
    ownerId: number; // TODO: esto en realidad no va aca creo, se maneja con el token del usuario
    images: FileList;
}

export interface UpdateArticleParameters {
    url: URL;
    title: string;
    description: string;
    pricePerDay: number;
    rating: number;
    categories: number[];
}
