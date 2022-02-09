export interface User {
	firstName: string;
	lastName: string;
	email: string;
	owner: boolean;
	pendingRequestAmount: number;
	acceptedRequestAmount: number;
	declinedRequestAmount: number;
	url: URL;
	imageUrl: URL;
	locationUrl: URL;
	id: number;
}

export interface CreateUserParameters {
	firstName: string;
	lastName: string;
	email: string;
	password: string;
	confirmPassword: string;
	isOwner: boolean;
	location: string;
	image: File;
}

export interface UpdateUserParameters {
	firstName: string;
	lastName: string;
	location: string;
	url: URL;
}

export interface UpdatePasswordParameters {
	password: string;
	confirmPassword: string;
	url: URL;
}
