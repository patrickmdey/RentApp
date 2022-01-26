import LocalizedStrings from 'react-localization'
import {IStrings} from "./types";
import {esCollection} from "./translations/es/strings";
import {enCollection} from "./translations/en/strings";

const strings: IStrings = new LocalizedStrings({
    en: {collection: enCollection},
    es: {collection: esCollection},
})


export {strings};

