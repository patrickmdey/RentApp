import {createSlice, PayloadAction} from '@reduxjs/toolkit';
import {RootState} from "../../store";
import {strings} from "../../i18n/i18n";

interface LanguageState {
    lang: string
}

const I18nSlice = createSlice({
    name: 'i18n',
    initialState: {lang: "en"} as LanguageState,
    reducers: {
        setLanguage: (state, {payload: lang}: PayloadAction<string>) => {
            state.lang = lang;
            strings.setLanguage(lang)
        }

    }
});

export const {setLanguage} = I18nSlice.actions;
export const selectCurrentLanguage = (state: RootState) => state.i18n.lang
export default I18nSlice.reducer;
