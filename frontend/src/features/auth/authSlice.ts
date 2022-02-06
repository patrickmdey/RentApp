import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { RootState } from '../../store';

interface AuthState {
	token: string | null;
	rememberMe: boolean;
}

const LOCAL_STORAGE_KEY = 'TOKEN';

const authSlice = createSlice({
	name: 'auth',
	initialState: { token: window.localStorage.getItem(LOCAL_STORAGE_KEY) } as AuthState,
	reducers: {
		setCredentials: (state, { payload: { token, rememberMe } }: PayloadAction<AuthState>) => {
			if (token == null) window.localStorage.removeItem(LOCAL_STORAGE_KEY);
			else if (rememberMe) window.localStorage.setItem(LOCAL_STORAGE_KEY, token);

			state.token = token;
		}
	}
});

export const setCredentials = authSlice.actions.setCredentials;
export const selectCurrentUser = (state: RootState) => state.auth.token;

export default authSlice.reducer;
