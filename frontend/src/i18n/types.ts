import {LocalizedStringsMethods} from 'react-localization'

export interface HeaderStrings {
    requests: string,
    logout: string,
    profile: string,
    login: string,
    signup: string,
    marketplace: string,
    publishArticle: string,
}

export interface StringCollection {
    header: HeaderStrings
}

export interface IStrings extends LocalizedStringsMethods {
    collection: StringCollection
}

