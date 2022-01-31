import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { RootState } from '../../store';

interface AuthState {
	token: string | null;
}

const authSlice = createSlice({
	name: 'auth',
	initialState: { token: null } as AuthState,
	reducers: {
		setCredentials: (state, { payload: { token } }: PayloadAction<AuthState>) => {
			state.token = token;
		}
	}
});

export const setCredentials = authSlice.actions.setCredentials;
export const selectCurrentUser = (state: RootState) => state.auth.token;

export default authSlice.reducer;
