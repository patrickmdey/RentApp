export interface Article {
	title: string;
	description: string;
	pricePerDay: number;
	timesRented: number;
	timesReviewed: number;
	rating: number;
	url: string;
	ownerUrl: string;
	imagesUrl: string;
	categoriesUrl: string;
	reviewsUrl: string;
	id: number;
}

export interface ListArticleParameters {
	page?: number;
	limit?: number;
	orderBy?: string;
	user?: number;
	renter?: number;
	name?: string;
	initPrice?: number;
	endPrice?: number;
	category?: number;
	location?: string;
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
	url: string;
	title: string;
	description: string;
	pricePerDay: number;
	rating: number;
	categories: number[];
}
