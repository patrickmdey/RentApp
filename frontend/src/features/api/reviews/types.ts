import { Url } from 'url';

export interface Review {
	rating: Number;
	message: String;
	createdAt: Date;
	url: Url;
	articleUrl: Url;
	renterUrl: Url;
}

export interface ListReviewParameters {
	articleId: Number;
	page?: Number;
}

export interface CreateReviewParameters {
	rating: Number;
	message: String;
	articleId: Number;
	renterId: Number; // TODO: esto seria el token de auth en realidad
}

export interface UpdateReviewParameters {
	rating: Number;
	message: String;
	url: URL;
}
