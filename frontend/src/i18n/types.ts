import {LocalizedStringsMethods} from 'react-localization'

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


export interface StringCollection {
    header: HeaderStrings,
    footer: FooterStrings,
    filter: FilterStrings
}

export interface IStrings extends LocalizedStringsMethods {
    collection: StringCollection,

}

