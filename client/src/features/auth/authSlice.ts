import { createSlice, PayloadAction } from '@reduxjs/toolkit'

interface AuthState {
  token: string | null
}

const AuthSlice = createSlice({
  name: 'auth',
  initialState: { user: null, token: null } as AuthState,
  reducers: {
    setCredentials: (
      state,
      { payload: { token } }: PayloadAction<{ token: string }>
    ) => {
      state.token = token
    },
  },
})

export const { setCredentials } = AuthSlice.actions

export default AuthSlice.reducer

