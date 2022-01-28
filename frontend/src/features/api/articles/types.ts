export interface Article {
	title: String;
	description: String;
	pricePerDay: Number;
	timesRented: Number;
	timesReviewed: Number;
	rating: Number;
	url: URL;
	ownerUrl: URL;
	imagesUrl: URL;
	categoriesUrl: URL;
	reviewsUrl: URL;
}

export interface ListArticleParameters {
	url?: URL;
}

export interface CreateArticleParameters {
	title: String;
	description: String;
	pricePerDay: Number;
	rating: Number;
	categories: Number[];
	ownerId: Number; // TODO: esto en realidad no va aca creo, se maneja con el token del usuario
	images: File[];
}

export interface UpdateArticleParameters {
	url: URL;
	title: String;
	description: String;
	pricePerDay: Number;
	rating: Number;
	categories: Number[];
}
