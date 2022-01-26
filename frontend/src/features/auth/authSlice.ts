import {createSlice, PayloadAction} from '@reduxjs/toolkit'
import {RootState} from "../../store";

interface AuthState {
  token: string | null
}

const AuthSlice = createSlice({
  name: 'auth',
  initialState: {user: null, token: "123"} as AuthState,
  reducers: {
    setCredentials: (
        state,
        {payload: {token}}: PayloadAction<{ token: string }>
    ) => {
      state.token = token
    },
  },
})

export const {setCredentials} = AuthSlice.actions;
export const selectCurrentUser = (state: RootState) => state.auth.token;
export default AuthSlice.reducer;

