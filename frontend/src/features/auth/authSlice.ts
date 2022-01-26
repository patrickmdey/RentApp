import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import {RootState} from "../../store";

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
export const selectCurrentUser = (state: RootState) => state.auth.token;

export default AuthSlice.reducer;
