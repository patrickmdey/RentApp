import { createSlice, PayloadAction } from '@reduxjs/toolkit';

interface UserPayload {
	email: string;
	password: string;
}
interface AuthState {
	token: string | null;
	user: UserPayload | null;
}

const AuthSlice = createSlice({
	name: 'auth',
	initialState: { token: null, user: null } as AuthState,
	reducers: {
		setCredentials: (state, { payload: { token, user } }: PayloadAction<AuthState>) => {
			state.user = user;
			state.token = token;
		}
	}
});

export const { setCredentials } = AuthSlice.actions;

export default AuthSlice.reducer;
