export interface User {
	firstName: String;
	lastName: String;
	email: String;
	isOwner: Boolean;
	pendingRequestAmount: Number;
	acceptedRequestAmount: Number;
	declinedRequestAmount: Number;
	url: URL;
	imageUrl: URL;
	locationUrl: URL;
}

export interface CreateUserParameters {
	firstName: String;
	lastName: String;
	email: String;
	password: String;
	isOwner: Boolean;
	location: Number;
	imageFormData: FormData;
}

export interface UpdateUserParameters {
	firstName: String;
	lastName: String;
	location: Number;
	url: URL;
}
