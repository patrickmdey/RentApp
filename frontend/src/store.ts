import {configureStore} from '@reduxjs/toolkit';
import {BaseApiSlice} from './features/api/baseApiSlice';
import authReducer from './features/auth/authSlice';
import i18nReducer from './features/i18n/i18nSlice'

export const store = configureStore({
    reducer: {
        auth: authReducer,
        i18n: i18nReducer,
        [BaseApiSlice.reducerPath]: BaseApiSlice.reducer
    },
    middleware: (getDefaultMiddleware) => {
        return getDefaultMiddleware().concat(BaseApiSlice.middleware);
    }
});

// Infer the `RootState` and `AppDispatch` types from the store itself
export type RootState = ReturnType<typeof store.getState>;
// Inferred type: {posts: PostsState, comments: CommentsState, users: UsersState}
export type AppDispatch = typeof store.dispatch;
