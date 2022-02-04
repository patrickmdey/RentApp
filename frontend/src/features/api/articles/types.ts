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
	id: number;
}

export interface ListArticleParameters {
	page?: number;
	orderBy?: number;
}

export interface CreateArticleParameters {
	title: String;
	description: String;
	pricePerDay: Number;
	rating: Number;
	categories: Number[];
	ownerId: Number; // TODO: esto en realidad no va aca creo, se maneja con el token del usuario
	images: FileList;
}

export interface UpdateArticleParameters {
	url: URL;
	title: String;
	description: String;
	pricePerDay: Number;
	rating: Number;
	categories: Number[];
}
