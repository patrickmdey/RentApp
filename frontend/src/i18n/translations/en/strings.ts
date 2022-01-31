import { StringCollection } from '../../types'

let enCollection: StringCollection = {
    header: {
        requests: "My requests",
        logout: "Log out",
        profile: "My profile",
        login: "Log in",
        signup: "Register",
        marketplace: "Explore",
        publishArticle: "Publish article",
    },
    footer: {
        team: "Team members",
        contact: "Contact",
        spanish: "Spanish",
        english: "English",
        email: "Email",
        language: "Language"
    },
    filter: {
        title: "Filters",
        name: "Name",
        category: "Category",
        orderBy: "Order by",
        location: "Location",
        minPrice: "Min. price",
        maxPrice: "Max. price",
        button: "Search",
        everyLocation: "Every location",
        everyCategory: "Every category"
    },
    login: {
        title: "LogIn",
        email: "Email*",
        emailPlaceholder: "Enter your email",
        password: "Password*",
        passwordPlaceholder: "Enter your password",
        rememberMe: "Remember me",
        loginButton: "LogIn",
        signupButton: "Register",
        error: "Email or password is incorrect"
    },
    article: {
        ownerCardTitle: "Owner",
        descriptionTitle: "Description",
        rent: "rent",
        noArticles: "There are no articles yet",
        createArticle: {
            title: "Publish Article",
            create: "Publish",
            pricePerDay: "PricePerDay*",
            articleName: "Name*",
            articleNameLabel: "Enter the article name",
            articleDescription: "Description*",
            articleDescriptionLabel: "Enter the article description",
            selectCategory: "Select at least one category",
            selectImage: "Upload at least one image"
        }
    },
    review: {
        noReviews: "There are no reviews yet",
        reviews: "Reviews"
    },
    categories: {
        searchByCategories: "Search by categories",
        // category: {
        //     Kitchen: "Kitchen",
        //     Travel: "Travel",
        //     Tools: "Tools",
        //     Saling: "Saling",
        //     Technology: "Technology",
        //     Cars: "Cars",
        //     Camping: "Camping"
        // }
    },
    landing: {
        title: "Welcome to RentApp",
        explanation: "In this app you will be able to search and rent any kind of articles just at a click of a button away. Select the one you like the most, the date you need it and Rentapp will put you in contact with the owner",
        button: "View Articles",
        titleLoggedIn: "Welcome {0}",
        textNoRequests: "You have no pending requests",
        pendingRequests: "You have {0} pending rent request",
        acceptedRequests: "You have {0} accepted rent requests",
        rejectedRequests: "You have {0} rejected rent requests",
        viewRequestsButton: "View requests"
    },
    register: {
        title: "Register",
        firstName: "Name*",
        firstNamePlaceholder: "Enter your name",
        lastName: "Last name*",
        lastNamePlaceholder: "Enter your last name",
        email: "Email*",
        emailPlaceholder: "Enter your email",
        location: "Location*",
        password: "Password*",
        passwordPlaceholder: "Enter your password",
        confirmPassword: "Confirm password*",
        confirmPasswordPlaceholder: "Reenter your password",
        image: "Upload image",
        isRenter: "Would you like to rent your stuff? ",
        confirmButton: "Confirm"
    }

}


export { enCollection }