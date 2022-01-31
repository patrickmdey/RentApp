import { StringCollection } from '../../types'

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
        error: "El usuario o la contraseña no son válidos"
    },
    article: {
        ownerCardTitle: "Dueño",
        descriptionTitle: "Descripción",
        rent: "Alquilar",
        noArticles: "Todavía no hay artículos",
        createArticle: {
            title: "Publicar artículo",
            create: "Publicar",
            pricePerDay: "Precio por día*",
            articleName: "Nombre*",
            articleNameLabel: "Ingresa el nombre del artículo",
            articleDescription: "Descripción*",
            articleDescriptionLabel: "Ingresa la descripción del artículo",
            selectCategory: "Seleccioná al menos una categoría",
            selectImage: "Seleccioná al menos una imagen"
        }
    },
    review: {
        noReviews: "Todavía no hay reseñas",
        reviews: "Reseñas"
    },
    categories: {
        searchByCategories: "Buscar por categorías",
        // category: {
        //     Kitchen: "Cocina",
        //     Travel: "Viajes",
        //     Tools: "Herramientas",
        //     Saling: "Navegación",
        //     Technology: "Tecnología",
        //     Cars: "Autos",
        //     Camping: "Campamento"
        // }
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
        confirmButton: "Confirmar"
    },
    requestCard: {
        rejectButton: "Rechazar",
        acceptButton:"Aceptar",
        messageTitle:"Mensaje:",
        endDate: "Fecha de finalización: {0}",
        startDate: "Fecha de inicio: {0}",
        name: "Nombre: {0} {1}",
        rejectTitle:"Rechazar solicitud",
        rejectText:"¿Estás seguro de que quieres rechazar esta solicitud?"
    }

}


export { esCollection }