import { StringCollection } from '../../types';

let enCollection: StringCollection = {
	header: {
		requests: 'My requests',
		logout: 'Log out',
		profile: 'My profile',
		login: 'Log in',
		signup: 'Register',
		marketplace: 'Explore',
		publishArticle: 'Publish article'
	},
	pageTitles: {
		login: 'Login',
		marketplace: 'Marketplace',
		editProfile: 'Edit Profile',
		createArticle: 'Publish Article',
		editArticle: 'Edit Article',
		myProfile: 'My Profile',
		register: 'Register',
		rentapp: 'RentApp'
	},
	footer: {
		team: 'Team members',
		contact: 'Contact',
		spanish: 'Spanish',
		english: 'English',
		email: 'Email',
		language: 'Language'
	},
	filter: {
		title: 'Filters',
		name: 'Name',
		category: 'Category',
		orderBy: 'Order by',
		location: 'Location',
		minPrice: 'Min. price',
		maxPrice: 'Max. price',
		button: 'Search',
		everyLocation: 'Every location',
		everyCategory: 'Every category'
	},

	filterInfo:{
		query: 'Name',
		filtering: 'Filtering by:',
		location: 'in',
		search: 'Showing results for:',
		clear: 'clear'
	},

	login: {
		title: 'LogIn',
		email: 'Email*',
		emailPlaceholder: 'Enter your email',
		password: 'Password*',
		passwordPlaceholder: 'Enter your password',
		rememberMe: 'Remember me',
		loginButton: 'LogIn',
		signupButton: 'Register',
		errors: {
			email: { required: 'You must enter your email' },
			password: { required: 'You must enter your password' },
			loginFail: 'Email or password is incorrect'
		}
	},
	profile: {
		profile: 'Profile',
		passwordForm: {
			password: 'Password',
			confirmPassword: 'Confirm password',
			errors: {
				fieldsMatch: 'Passwords must match'
			},
			cancel: 'Cancel',
			save: 'Save'
		},
		deleteForm: {
			message: 'Delete user',
			confirmation: 'Are you sure you want to delete your account?',
			cancel: 'Cancel',
			confirm: 'Delete'
		},
		createdArticles: 'Published Articles',
		rentedArticles: 'Rented Articles'
	},
	article: {
		ownerCardTitle: 'Owner',
		descriptionTitle: 'Description',
		relatedTitle: 'Other users were also interested in',
		rent: 'Rent',
		edit: 'Edit',
		createReview: 'Review',
		noArticles: 'There are no articles yet',
		timesRented: 'rents',
		createArticle: {
			title: 'Publish Article',
			create: 'Publish',
			pricePerDay: 'PricePerDay*',
			articleName: 'Name*',
			articleNameLabel: 'Enter the article name',
			articleDescription: 'Description*',
			articleDescriptionLabel: 'Enter the article description',
			selectCategory: 'Select at least one category',
			selectImage: 'Upload at least one image',
			errors: {
				title: {
					required: 'You must enter a name for the article',
					minLength: 'The name of the article must have between {minLength} and {maxLength} characters',
					maxLength: 'The name of the article must have between {minLength} and {maxLength} characters'
				},
				description: {
					required: 'You must enter a decription for the article',
					minLength: 'The description must have between {minLength} and {maxLength} characters',
					maxLength: 'The description must have between {minLength} and {maxLength} characters'
				},
				categories: { required: 'You must select at least one category' },
				pricePerDay: { min: 'The price must be greater than {min}' },
				images: 'You must upload at least one image'
			}
		},
		editArticle: {
			errors: {
				unauthorized: 'The article you are trying to edit is not yours',
				back: 'Back',
				view: 'View article'
			}
		},
		requestArticle: {
			title: 'Request Article',
			startDate: 'Start Date*',
			endDate: 'End Date*',
			message: 'Message*',
			messagePlaceHolder: 'Enter a message so that the owner coinsider your request',
			send: 'Send',
			errors: {
				startDate: {
					required: 'You must enter the start date'
				},
				endDate: {
					required: 'You must enter an end date'
				},
				message: {
					required: 'You must enter a message for the owner',
					minLength: 'The message must have more than {minLength} characters'
				}
			},
			successTitle: "The request has been sent. Soon you'll receive news from the owner",
			successSubtitle: 'For more articles click here'
		}
	},
	review: {
		reviews: 'Reviews',
		create: 'Create review',
		edit: 'Edit review'
	},
	categories: {
		searchByCategories: 'Search by categories'
	},
	landing: {
		title: 'Welcome to RentApp',
		explanation:
			'In this app you will be able to search and rent any kind of articles just at a click of a button away. Select the one you like the most, the date you need it and Rentapp will put you in contact with the owner',
		button: 'View Articles',
		titleLoggedIn: 'Welcome {0}',
		textNoRequests: 'You have no pending requests',
		pendingRequests: 'You have {0} pending rent request',
		acceptedRequests: 'You have {0} accepted rent requests',
		rejectedRequests: 'You have {0} rejected rent requests',
		viewRequestsButton: 'View requests',
		topRated: 'Top rated articles',
		mostRented: 'Most rented articles'
	},
	register: {
		title: 'Register',
		firstName: 'Name*',
		firstNamePlaceholder: 'Enter your name',
		lastName: 'Last name*',
		lastNamePlaceholder: 'Enter your last name',
		email: 'Email*',
		emailPlaceholder: 'Enter your email',
		location: 'Location*',
		selectLocation: 'Select',
		password: 'Password*',
		passwordPlaceholder: 'Enter your password',
		confirmPassword: 'Confirm password*',
		confirmPasswordPlaceholder: 'Confirm your password',
		image: 'Upload image',
		isRenter: 'Would you like to rent your stuff? ',
		confirmButton: 'Confirm',
		errors: {
			firstName: {
				required: 'You must enter your first name',
				minLength: 'First name must have between {minLength} and {maxLength} characters',
				maxLength: 'First name must have between {minLength} and {maxLength} characters'
			},
			lastName: {
				required: 'You must enter your last name',
				minLength: 'Last name must have between {minLength} and {maxLength} characters',
				maxLength: 'Last name must have between {minLength} and {maxLength} characters'
			},
			location: { required: 'You must select your location' },
			image: { required: 'You must upload an image' }
		}
	},
	requestCard: {
		rejectButton: 'Reject',
		acceptButton: 'Accept',
		messageTitle: 'Message:',
		endDate: 'End date: {0}',
		startDate: 'Start date: {0}',
		name: 'Name: {0} {1}',
		rejectTitle: 'Reject request',
		rejectText: 'Are you sure you want to reject this request?'
	},

	requests: {
		title: 'My Requests',
		sentTitle: 'Sent',
		receivedTitle: 'Received',
		pendingTitle: 'Pending',
		acceptedTitle: 'Accepted',
		declinedTitle: 'Declined',
		accept: 'Accept',
		decline: 'Decline',
		message: 'Message',
		startDate: 'Start Date',
		endDate: 'End Date',
		name: 'Name',
		declineTitle: 'Decline Request',
		declineWarning: 'Are you sure you want to reject this request?',
		received: {
			noPendingTitle: "You don't have pending requests",
			noPendingSubtitle: 'Have you tried publishing an article?',
			noAcceptedTitle: "Here you'll see the requests that you have accepted",
			noAcceptedSubtitle: "You haven't accepted any requests yet",
			noDeclinedTitle: "You haven't rejected any requests yet",
			noDeclinedSubtitle: "Here you'll see the requests that you have rejected"
		},
		sent: {
			noPendingTitle: "You haven't sent any request yet",
			noPendingSubtitle: 'Have you tried renting an article ?',
			noAcceptedTitle: 'None of your requests has been accepted',
			noAcceptedSubtitle: "Here you'll see the requests that you have got accepted",
			noDeclinedTitle: 'None of your requests has been declined',
			noDeclinedSubtitle: "Here you'll see the requests that you have got rejected"
		}
	},

	noData: {
		articleNotFoundTitle: 'No articles match your search',
		articleNotFoundSubtitle: 'Try using generic words',
		noReviews: 'There are no reviews yet',
		noMyProfileArticlesTitle: "You haven't published any article yet",
		noMyProfileRentedArticlesTitle: "You haven't rented any article yet",
		noMyProfileSubtitle: 'You can do so by clicking here'
	},

	forms: {
		cancel: 'Cancel',
		save: 'Save'
	},
	errors: {
		articleNotFound: 'Article not found',
		userNotFound: 'User not found',
		locationNotFound: 'Location not found',
		categoryNotFound: 'Category not found',
		rentProposalNotFound: 'Rent proposal not found',
		reviewNotFound: 'CreateReview not found',
		cannotCreateArticle: 'Cannot create article',
		cannotCreateProposal: 'Cannot create proposal',
		unableToSendEmail: 'Unable to send email',
		cannotCreateUser: 'Cannot create user',
		cannotCreateReview: 'Cannot create review',
		cannotCreateImage: 'Cannot create image',
		cannotEditArticle: 'Cannot edit article',
		cannotEditArticleCategory: 'Cannot edit article category',
		cannotEditRequest: 'Cannot edit request',
		cannotEditReview: 'Cannot edit review',
		cannotEditUser: 'Cannot edit user',
		unexpected: 'Unexpected error',
		imageNotFound: 'Image not found',
		pageNotFoundTitle: 'The requested URL does not exist',
		pageNotFoundSubtitle: 'Return to home screen'
	}
};

export { enCollection };
