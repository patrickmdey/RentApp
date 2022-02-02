import {StringCollection} from '../../types'

let esCollection: StringCollection = {
    header: {
        requests: "Mis solicitudes",
        logout: "Cerrar sesión",
        profile: "Mi perfíl",
        login: "Ingresar",
        signup: "Crear cuenta",
        marketplace: "Explorar",
        publishArticle: "Publicar artículo",
    },
    footer: {
        team: "Equipo",
        contact: "Contacto",
        spanish: "Español",
        english: "Inglés",
        email: "Mail",
        language: "Idioma"
    },
    filter: {
        title: "Filtros",
        name: "Nombre",
        category: "Categoría",
        orderBy: "Ordenar por",
        location: "Localización",
        minPrice: "Precio mínimo",
        maxPrice: "Precio máximo",
        button: "Buscar",
        everyLocation: "Todas las ubicaciones",
        everyCategory: "Todas las categorías"
    },
    login: {
        title: "Iniciar Sesión",
        email: "Email*",
        emailPlaceholder: "Ingresa tu email",
        password: "Contraseña*",
        passwordPlaceholder: "Ingresa tu contraseña",
        rememberMe: "Recordar mi sesión",
        loginButton: "Iniciar Sesión",
        signupButton: "Registrar Usuario",
        errors: {
            email: "Es obligatorio ingresar un email",
            password: "Es obligatorio ingresar una contraseña"
        },
        error: "El usuario o la contraseña no son válidos"
    },
    article: {
        ownerCardTitle: "Dueño",
        descriptionTitle: "Descripción",
        rent: "Alquilar",
        noArticles: "Todavía no hay artículos",
        timesRented: "alquileres",
        createArticle: {
            title: "Publicar artículo",
            create: "Publicar",
            pricePerDay: "Precio por día*",
            articleName: "Nombre*",
            articleNameLabel: "Ingresa el nombre del artículo",
            articleDescription: "Descripción*",
            articleDescriptionLabel: "Ingresa la descripción del artículo",
            selectCategory: "Seleccioná al menos una categoría",
            selectImage: "Seleccioná al menos una imagen",
            errors: {
                title: "Es obligatorio ingresar el nombre del artículo",
                titleLength: "El nombre del artículo debe tener entre {2} y {1} caracteres",
                description: "La descripción debe tener entre {2} y {1} caracteres",
                categories: "Es obligatorio seleccionar al menos una categoría",
                pricePerDay: "El monto debe ser mayor a {1}",
                images: "Es obligatorio subir al menos una imagen"
            }
        },
        requestArticle: {
            title: "Solicitar artículo",
            startDate: "Fecha de Inicio*",
            endDate: "Fecha de Finalización*",
            message: "Mensaje*",
            messagePlaceHolder: "Ingresa un mensaje para que el Dueño tenga mas en cuenta tu solicitud",
            send: "Enviar",
            errors: {
                startDate: "Es obligatorio ingresar la fecha de inicio",
                endDate: "Es obligatorio ingresar la fecha de finalización",
                message: "Es obligatorio ingresar un mensaje para el dueño",
            }
        }
    },
    review: {
        noReviews: "Todavía no hay reseñas",
        reviews: "Reseñas"
    },
    categories: {
        searchByCategories: "Buscar por categorías",
    },
    landing: {
        title: "Bienvenido a RentApp",
        explanation: "En esta aplicación podras buscar y alquilar todo tipo de artículos al alcance de un click. Seleccioná el que más te gusta, la fecha en la que lo necesitas y RentApp te pondrá en contacto con su dueño",
        button: "Ver artículos",
        titleLoggedIn: "Bienvenido {0}",
        textNoRequests: "No tienes solicitudes pendientes",
        pendingRequests: "Tienes {0} solicitud de alquiler pendiente",
        acceptedRequests: "Te han aceptado {0} solicitudes de alquiler",
        rejectedRequests: "Te han rechazado {0} solicitudes de alquiler",
        viewRequestsButton: "Ver Solicitudes"
    },
    register: {
        title: "Registrar Usuario",
        firstName: "Nombre*",
        firstNamePlaceholder: "Ingresa tu nombre",
        lastName: "Apellido*",
        lastNamePlaceholder: "Ingresa tu apellido",
        email: "Email*",
        emailPlaceholder: "Ingresa tu email",
        location: "Localización*",
        password: "Contraseña*",
        passwordPlaceholder: "Ingresa tu contraseña",
        confirmPassword: "Confirmar contraseña*",
        confirmPasswordPlaceholder: "Reingresa tu contraseña",
        image: "Seleccioná una imagen",
        isRenter: "¿Quieres alquilar tus cosas? ",
        confirmButton: "Confirmar",
        errors: {
            firstName: "Es obligatorio ingresar tu nombre",
            lastName: "Es obligatorio ingresar tu apellido",
            location: "Es obligatorio seleccionar tu ubicación",
            image: "Es obligatorio elegir una imagen"
        }
    },
    requestCard: {
        rejectButton: "Rechazar",
        acceptButton: "Aceptar",
        messageTitle: "Mensaje:",
        endDate: "Fecha de finalización: {0}",
        startDate: "Fecha de inicio: {0}",
        name: "Nombre: {0} {1}",
        rejectTitle: "Rechazar solicitud",
        rejectText: "¿Estás seguro de que quieres rechazar esta solicitud?"
    }

}


export {esCollection}