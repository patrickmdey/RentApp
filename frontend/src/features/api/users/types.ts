export interface User {
    firstName: string;
    lastName: string;
    email: string;
    isOwner: Boolean;
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
    location: number;
    image: File;
}

export interface UpdateUserParameters {
    firstName: string;
    lastName: string;
    location: number;
    url: URL;
}
