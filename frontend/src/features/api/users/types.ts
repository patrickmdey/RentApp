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
    id: number;
}

export interface CreateUserParameters {
    firstName: string;
    lastName: string;
    email: string;
    password: string;
    confirmPassword: string;
    isOwner: boolean;
    location: number;
    image: File;
}

export interface UpdateUserParameters {
    firstName: String;
    lastName: String;
    location: Number;
    url: URL;
}
