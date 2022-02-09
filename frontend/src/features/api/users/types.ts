export interface User {
	firstName: string;
	lastName: string;
	email: string;
	owner: boolean;
	pendingRequestAmount: number;
	acceptedRequestAmount: number;
	declinedRequestAmount: number;
	url: string;
	imageUrl: string;
	locationUrl: string;
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
	url: string;
}

export interface UpdatePasswordParameters {
	password: string;
	confirmPassword: string;
	url: string;
}
