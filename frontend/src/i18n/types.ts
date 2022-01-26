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


export interface StringCollection {
    header: HeaderStrings,
    footer: FooterStrings
}

export interface IStrings extends LocalizedStringsMethods {
    collection: StringCollection,

}

