import { LocalizedStringsMethods } from 'react-localization'

interface HeaderStrings {
    requests: string,
    logout: string,
    profile: string,
    login: string,
    signup: string,
    marketplace: string,
    publishArticle: string,
}

interface FooterStrings {
    team: string,
    contact: string,
    spanish: string,
    english: string,
    email: string,
    language: string
}

interface FilterStrings {
    title: string,
    name: string,
    category: string,
    orderBy: string,
    location: string,
    minPrice: string,
    maxPrice: string,
    button: string,
    everyLocation: string,
    everyCategory: string
}

interface LogInStrings {
    title: string,
    email: string,
    emailPlaceholder: string,
    password: string,
    passwordPlaceholder: string,
    rememberMe: string,
    loginButton: string,
    signupButton: string,
    error: string
}

interface ArticleStrings {
    ownerCardTitle: string,
    descriptionTitle: string,
    rent: string,
    noArticles: string,
    createArticle: {
        title: string,
        create: string,
        articleName: string,
        articleNameLabel: string,
        articleDescription: string,
        articleDescriptionLabel: string,
        pricePerDay: string,
        selectCategory: string,
        selectImage: string,

    }

}

interface ReviewStrings {
    noReviews: string,
    reviews: string
}

interface CategoriesString {
    searchByCategories: string
}

export interface StringCollection {
    header: HeaderStrings,
    footer: FooterStrings,
    filter: FilterStrings,
    login: LogInStrings,
    article: ArticleStrings,
    review: ReviewStrings,
    categories: CategoriesString
}

export interface IStrings extends LocalizedStringsMethods {
    collection: StringCollection,

}

