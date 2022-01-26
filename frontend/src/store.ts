import {combineReducers, configureStore} from '@reduxjs/toolkit';
import {BaseApiSlice} from './features/api/baseApiSlice';
import authReducer from './features/auth/authSlice';
import i18nReducer from './features/i18n/i18nSlice'
import {persistReducer, persistStore} from 'redux-persist'
import storage from 'redux-persist/lib/storage'
import hardSet from "redux-persist/es/stateReconciler/hardSet";

const persistConfig = {
    key: 'i18n',
    storage,
    version: 1,
    hardSet,
    whitelist: ["i18n"]
}

const baseReducers = combineReducers({
    auth: authReducer,
    i18n: i18nReducer,
    [BaseApiSlice.reducerPath]: BaseApiSlice.reducer
})

const persistedReducer = persistReducer(persistConfig, baseReducers)

const store = configureStore({
    reducer: persistedReducer,
    middleware: [BaseApiSlice.middleware]
});

const persistor = persistStore(store)

// Infer the `RootState` and `AppDispatch` types from the store itself
export type RootState = ReturnType<typeof store.getState>;
// Inferred type: {posts: PostsState, comments: CommentsState, users: UsersState}
export type AppDispatch = typeof store.dispatch;

export {store, persistor}