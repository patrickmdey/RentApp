import { LocalizedStringsMethods } from 'react-localization';

export interface ErrorMessageInterface {
	max?: string;
	min?: string;
	maxLength?: string;
	minLength?: string;
	required?: string;
	pattern?: string;
	onBlur?: string;
	disabled?: string;
	value?: string;
	onChange?: undefined;
	validate?: undefined;
	valueAsNumber?: undefined;
	valueAsDate?: undefined;
	setValueAs?: undefined;
	shouldUnregister?: undefined;
	deps?: undefined;
}

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

interface PageTitlesString {
	login: string;
	marketplace: string;
	editProfile: string;
	createArticle: string;
	editArticle: string;
	myProfile: string;
	register: string;
	rentapp: string;
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
		email: ErrorMessageInterface;
		password: ErrorMessageInterface;
		loginFail: string;
	};
}

export interface ProfileString {
	profile: string;
	passwordForm: {
		password: string;
		confirmPassword: string;
		errors: {
			fieldsMatch: string;
		};
		cancel: string;
		save: string;
	};
	deleteForm: {
		message: string;
		confirmation: string;
		cancel: string;
		confirm: string;
	};
	createdArticles: string;
	rentedArticles: string;
}

interface ArticleStrings {
	ownerCardTitle: string;
	relatedTitle: string;
	descriptionTitle: string;
	rent: string;
	edit: string;
	createReview: string;
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
			title: ErrorMessageInterface;
			description: ErrorMessageInterface;
			pricePerDay: ErrorMessageInterface;
			categories: ErrorMessageInterface;
			images: string;
		};
	};
	editArticle: {
		errors: {
			unauthorized: string;
			back: string;
			view: string;
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
			startDate: ErrorMessageInterface;
			endDate: ErrorMessageInterface;
			message: ErrorMessageInterface;
		};
		successTitle: string;
		successSubtitle: string;
	};
}

interface ReviewStrings {
	reviews: string;
	create: string;
	edit: string;
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
	topRated: string;
	mostRented: string;
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
		firstName: ErrorMessageInterface;
		lastName: ErrorMessageInterface;
		location: ErrorMessageInterface;
		image: ErrorMessageInterface;
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

export interface NoDataString {
	articleNotFoundTitle: string;
	articleNotFoundSubtitle: string;
	noReviews: string;
	noMyProfileArticlesTitle: string;
	noMyProfileSubtitle: string;
	noMyProfileRentedArticlesTitle: string;
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

export interface FormsString {
	cancel: string;
	save: string;
}

export interface ErrorsString {
	articleNotFound: string;
	userNotFound: string;
	locationNotFound: string;
	categoryNotFound: string;
	rentProposalNotFound: string;
	reviewNotFound: string;
	cannotCreateArticle: string;
	cannotCreateProposal: string;
	unableToSendEmail: string;
	cannotCreateUser: string;
	cannotCreateReview: string;
	cannotCreateImage: string;
	cannotEditArticle: string;
	cannotEditArticleCategory: string;
	cannotEditRequest: string;
	cannotEditReview: string;
	cannotEditUser: string;
	unexpected: string;
	imageNotFound: string;
	pageNotFoundTitle: string;
	pageNotFoundSubtitle: string;
}

export interface StringCollection {
	header: HeaderStrings;
	footer: FooterStrings;
	filter: FilterStrings;
	login: LogInStrings;
	profile: ProfileString;
	article: ArticleStrings;
	review: ReviewStrings;
	categories: CategoriesString;
	landing: LandingString;
	register: RegisterString;
	requestCard: RequestCardString;
	requests: RequestsString;
	pageTitles: PageTitlesString;
	noData: NoDataString;
	forms: FormsString;
	errors: ErrorsString;
}

export interface IStrings extends LocalizedStringsMethods {
	collection: StringCollection;
}
