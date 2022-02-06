import { LocalizedStringsMethods } from 'react-localization';

interface HeaderStrings {
	requests: string;
	logout: string;
	profile: string;
	login: string;
	signup: string;
	marketplace: string;
	publishArticle: string;
}

interface FooterStrings {
	team: string;
	contact: string;
	spanish: string;
	english: string;
	email: string;
	language: string;
}

interface FilterStrings {
	title: string;
	name: string;
	category: string;
	orderBy: string;
	location: string;
	minPrice: string;
	maxPrice: string;
	button: string;
	everyLocation: string;
	everyCategory: string;
}

interface LogInStrings {
	title: string;
	email: string;
	emailPlaceholder: string;
	password: string;
	passwordPlaceholder: string;
	rememberMe: string;
	loginButton: string;
	signupButton: string;
	errors: {
		email: string;
		password: string;
		loginFail: string;
	};
}

interface ArticleStrings {
	ownerCardTitle: string;
	descriptionTitle: string;
	rent: string;
	noArticles: string;
	timesRented: string;
	createArticle: {
		title: string;
		create: string;
		articleName: string;
		articleNameLabel: string;
		articleDescription: string;
		articleDescriptionLabel: string;
		pricePerDay: string;
		selectCategory: string;
		selectImage: string;
		errors: {
			title: string;
			titleLength: string;
			description: string;
			pricePerDay: string;
			categories: string;
			images: string;
		};
	};
	requestArticle: {
		title: string;
		startDate: string;
		endDate: string;
		message: string;
		messagePlaceHolder: string;
		send: string;
		errors: {
			startDate: string;
			endDate: string;
			message: string;
		};
	};
}

interface ReviewStrings {
	noReviews: string;
	reviews: string;
}

interface CategoriesString {
	searchByCategories: string;
}

export interface LandingString {
	title: string;
	explanation: string;
	button: string;
	titleLoggedIn: string;
	textNoRequests: string;
	pendingRequests: string;
	acceptedRequests: string;
	rejectedRequests: string;
	viewRequestsButton: string;
}

export interface RegisterString {
	title: string;
	firstName: string;
	firstNamePlaceholder: string;
	lastName: string;
	lastNamePlaceholder: string;
	email: string;
	emailPlaceholder: string;
	location: string;
	password: string;
	passwordPlaceholder: string;
	confirmPassword: string;
	confirmPasswordPlaceholder: string;
	image: string;
	isRenter: string;
	confirmButton: string;
	errors: {
		firstName: string;
		lastName: string;
		location: string;
		image: string;
	};
}

export interface RequestCardString {
	rejectButton: string;
	acceptButton: string;
	messageTitle: string;
	endDate: string;
	startDate: string;
	name: string;
	rejectTitle: string;
	rejectText: string;
}

export interface RequestsString {
	title: string;
	sentTitle: string;
	receivedTitle: string;
	pendingTitle: string;
	acceptedTitle: string;
	declinedTitle: string;
	accept: string;
	decline: string;
	message: string;
	startDate: string;
	endDate: string;
	name: string;
	declineTitle: string;
	declineWarning: string;
	received: {
		noPendingTitle: string;
		noPendingSubtitle: string;
		noAcceptedTitle: string;
		noAcceptedSubtitle: string;
		noDeclinedTitle: string;
		noDeclinedSubtitle: string;
	};
	sent: {
		noPendingTitle: string;
		noPendingSubtitle: string;
		noAcceptedTitle: string;
		noAcceptedSubtitle: string;
		noDeclinedTitle: string;
		noDeclinedSubtitle: string;
	};
}

export interface StringCollection {
	header: HeaderStrings;
	footer: FooterStrings;
	filter: FilterStrings;
	login: LogInStrings;
	article: ArticleStrings;
	review: ReviewStrings;
	categories: CategoriesString;
	landing: LandingString;
	register: RegisterString;
	requestCard: RequestCardString;
	requests: RequestsString;
}

export interface IStrings extends LocalizedStringsMethods {
	collection: StringCollection;
}
