import { configureStore } from '@reduxjs/toolkit';
import { BaseApiSlice } from './features/api/baseApiSlice';
import authReducer from './features/auth/authSlice';

export const store = configureStore({
	reducer: {
		auth: authReducer,
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
