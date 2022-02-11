import { StringCollection } from '../../types';

let esCollection: StringCollection = {
	header: {
		requests: 'Mis solicitudes',
		logout: 'Cerrar sesión',
		profile: 'Mi perfíl',
		login: 'Ingresar',
		signup: 'Crear cuenta',
		marketplace: 'Explorar',
		publishArticle: 'Publicar artículo'
	},
	pageTitles: {
		login: 'Iniciar sesión',
		marketplace: 'Marketplace',
		editProfile: 'Editar Perfil',
		createArticle: 'Publicar Artículo',
		editArticle: 'Editar Publicación',
		myProfile: 'Mi perfil',
		register: 'Registro',
		rentapp: 'RentApp'
	},
	footer: {
		team: 'Equipo',
		contact: 'Contacto',
		spanish: 'Español',
		english: 'Inglés',
		email: 'Mail',
		language: 'Idioma'
	},
	filter: {
		title: 'Filtros',
		name: 'Nombre',
		category: 'Categoría',
		orderBy: 'Ordenar por',
		location: 'Localización',
		minPrice: 'Precio mínimo',
		maxPrice: 'Precio máximo',
		button: 'Buscar',
		everyLocation: 'Todas las ubicaciones',
		everyCategory: 'Todas las categorías'
	},
	login: {
		title: 'Iniciar Sesión',
		email: 'Email*',
		emailPlaceholder: 'Ingresa tu email',
		password: 'Contraseña*',
		passwordPlaceholder: 'Ingresa tu contraseña',
		rememberMe: 'Recordar mi sesión',
		loginButton: 'Iniciar Sesión',
		signupButton: 'Registrar Usuario',
		errors: {
			email: {
				required: 'Es obligatorio ingresar un email',
				minLength: 'El mail debe tener entre {minLength} y {maxLength} caracteres',
				maxLength: 'El mail debe tener entre {minLength} y {maxLength} caracteres'
			},
			password: {
				required: 'Es obligatorio ingresar una contraseña',
				minLength: 'La contraseña debe tener entre {minLength} y {maxLength} caracteres',
				maxLength: 'La contraseña debe tener entre {minLength} y {maxLength} caracteres'
			},
			loginFail: 'El usuario o la contraseña ingresada son incorrectos'
		}
	},
	profile: {
		profile: 'Perfil',
		passwordForm: {
			password: 'Contraseña',
			confirmPassword: 'Confirmar contraseña',
			errors: {
				fieldsMatch: 'Las contraseñas deben coincidir'
			},
			cancel: 'Cancelar',
			save: 'Guardar'
		},
		deleteForm: {
			message: 'Eliminar usuario',
			confirmation: '¿Estás seguro de que quieres eliminar tu cuenta?',
			cancel: 'Cancelar',
			confirm: 'Eliminar'
		},
		createdArticles: 'Publicaciones',
		rentedArticles: 'Alquileres'
	},
	article: {
		ownerCardTitle: 'Dueño',
		descriptionTitle: 'Descripción',
		rent: 'Alquilar',
		edit: 'Editar',
		noArticles: 'Todavía no hay artículos',
		timesRented: 'alquileres',
		createArticle: {
			title: 'Publicar artículo',
			create: 'Publicar',
			pricePerDay: 'Precio por día*',
			articleName: 'Nombre*',
			articleNameLabel: 'Ingresa el nombre del artículo',
			articleDescription: 'Descripción*',
			articleDescriptionLabel: 'Ingresa la descripción del artículo',
			selectCategory: 'Seleccioná al menos una categoría',
			selectImage: 'Seleccioná al menos una imagen',
			errors: {
				title: {
					required: 'Es obligatorio ingresar el nombre del artículo',
					minLength: 'El nombre del artículo debe tener entre {minLength} y {maxLength} caracteres',
					maxLength: 'El nombre del artículo debe tener entre {minLength} y {maxLength} caracteres'
				},
				description: {
					required: 'Es obligatorio ingresar una descripcion para el articulo',
					minLength: 'La descripción debe tener entre {minLength} y {maxLength} caracteres',
					maxLength: 'La descripción debe tener entre {minLength} y {maxLength} caracteres'
				},
				categories: { required: 'Debes seleccionar al menos una categoria' },
				pricePerDay: { min: 'El monto debe ser mayor a {min}' },
				images: 'You must upload at least one image'
			}
		},
		requestArticle: {
			title: 'Solicitar artículo',
			startDate: 'Fecha de Inicio*',
			endDate: 'Fecha de Finalización*',
			message: 'Mensaje*',
			messagePlaceHolder: 'Ingresa un mensaje para que el Dueño tenga mas en cuenta tu solicitud',
			send: 'Enviar',
			errors: {
				startDate: { required: 'Es obligatorio ingresar la fecha de inicio' },
				endDate: { required: 'Es obligatorio ingresar la fecha de finalización' },
				message: { required: 'Es obligatorio ingresar un mensaje para el dueño' }
			}
		}
	},
	review: {
		reviews: 'Reseñas',
		create: 'Crear reseña',
		edit: 'Editar reseña'
	},
	categories: {
		searchByCategories: 'Buscar por categorías'
	},
	landing: {
		title: 'Bienvenido a RentApp',
		explanation:
			'En esta aplicación podras buscar y alquilar todo tipo de artículos al alcance de un click. Seleccioná el que más te gusta, la fecha en la que lo necesitas y RentApp te pondrá en contacto con su dueño',
		button: 'Ver artículos',
		titleLoggedIn: 'Bienvenido {0}',
		textNoRequests: 'No tienes solicitudes pendientes',
		pendingRequests: 'Tienes {0} solicitud de alquiler pendiente',
		acceptedRequests: 'Te han aceptado {0} solicitudes de alquiler',
		rejectedRequests: 'Te han rechazado {0} solicitudes de alquiler',
		viewRequestsButton: 'Ver Solicitudes',
		topRated: 'Artículos más votados',
		mostRented: 'Artículos más alquilados'
	},
	register: {
		title: 'Registrar Usuario',
		firstName: 'Nombre*',
		firstNamePlaceholder: 'Ingresa tu nombre',
		lastName: 'Apellido*',
		lastNamePlaceholder: 'Ingresa tu apellido',
		email: 'Email*',
		emailPlaceholder: 'Ingresa tu email',
		location: 'Localización*',
		password: 'Contraseña*',
		passwordPlaceholder: 'Ingresa tu contraseña',
		confirmPassword: 'Confirmar contraseña*',
		confirmPasswordPlaceholder: 'Reingresa tu contraseña',
		image: 'Seleccioná una imagen',
		isRenter: '¿Quieres alquilar tus cosas? ',
		confirmButton: 'Confirmar',
		errors: {
			firstName: {
				required: 'Es obligatorio ingresar tu nombre',
				minLength: 'El nombre debe tener entre {minLength} y {maxLength} caracteres'
			},
			lastName: {
				required: 'Es obligatorio ingresar tu apellido',
				minLength: 'El apellido debe tener entre {minLength} y {maxLength} caracteres'
			},
			location: { required: 'Es obligatorio seleccionar tu ubicación' },
			image: { required: 'Es obligatorio elegir una imagen' }
		}
	},
	requestCard: {
		rejectButton: 'Rechazar',
		acceptButton: 'Aceptar',
		messageTitle: 'Mensaje:',
		endDate: 'Fecha de finalización: {0}',
		startDate: 'Fecha de inicio: {0}',
		name: 'Nombre: {0} {1}',
		rejectTitle: 'Rechazar solicitud',
		rejectText: '¿Estás seguro de que quieres rechazar esta solicitud?'
	},

	requests: {
		title: 'Mis Solicitudes',
		sentTitle: 'Enviadas',
		receivedTitle: 'Recibidas',
		pendingTitle: 'Pendientes',
		acceptedTitle: 'Aceptadas',
		declinedTitle: 'Rechazadas',
		accept: 'Aceptar',
		decline: 'Rechazar',
		message: 'Mensaje',
		startDate: 'Fecha de inicio',
		endDate: 'Fecha de finalización',
		name: 'Nombre',
		declineTitle: 'Rechazar solicitud',
		declineWarning: '¿Estás seguro de que quieres rechazar esta solicitud?',
		received: {
			noPendingTitle: 'No tienes solicitudes pendientes',
			noPendingSubtitle: '¿Ya intentaste publicar un artículo?',
			noAcceptedTitle: 'No has aceptado ninguna solicitud',
			noAcceptedSubtitle: 'Aquí aparecerán las solicitudes que aceptaste',
			noDeclinedTitle: 'No has rechazado ninguna solicitud',
			noDeclinedSubtitle: 'Aquí aparecerán las solicitudes que rechazaste'
		},
		sent: {
			noPendingTitle: 'No has enviado ninguna solicitud',
			noPendingSubtitle: '¿Ya intentaste alquilar un artículo?',
			noAcceptedTitle: 'No han aceptado ninguna de tus solicitudes',
			noAcceptedSubtitle: 'Aquí aparecerán las solicitudes que te aceptaron',
			noDeclinedTitle: 'No han rechazado ninguna de tus solicitudes',
			noDeclinedSubtitle: 'Aquí aparecerán las solicitudes que te rechazaron'
		}
	},

	noData: {
		articleNotFoundTitle: 'No hay artículos que coincidan con tu búsqueda',
		articleNotFoundSubtitle: 'Prueba utilizar palabras mas genéricas',
		noReviews: 'Todavía no hay reseñas',
		noMyProfileArticlesTitle: 'Todavía no publicaste ningún artículo',
		noMyProfileSubtitle: 'Puedes hacerlo haciendo click aquí',
		noMyProfileRentedArticlesTitle: 'Todavía no alquilaste ningún artículo'
	},

	forms: {
		cancel: 'Cancelar',
		save: 'Guardar'
	},

	errors: {
		articleNotFound: 'No se encontró el artículo',
		userNotFound: 'No se encontró el usuario',
		locationNotFound: 'No se encontró la ubicación',
		categoryNotFound: 'No se encontró la categoría',
		rentProposalNotFound: 'No se encontró la propuesta de alquiler',
		reviewNotFound: 'No se encontró la reseña',
		cannotCreateArticle: 'No se pudo crear el artículo',
		cannotCreateProposal: 'No se pudo crear la propuesta',
		unableToSendEmail: 'No se pudo enviar el email',
		cannotCreateUser: 'No se pudo crear el usuario',
		cannotCreateReview: 'No se pudo crear la reseña',
		cannotCreateImage: 'No se pudo crear la imagen',
		cannotEditArticle: 'No se pudo editar el artículo',
		cannotEditArticleCategory: 'No se pudo editar la categoría del artículo',
		cannotEditRequest: 'No se pudo editar la solicitud',
		cannotEditReview: 'No se pudo editar la reseña',
		cannotEditUser: 'No se pudo editar el usuario',
		unexpected: 'Ocurrió un error inesperado',
		imageNotFound: 'No se encontró la imagen'
	}
};

export { esCollection };
