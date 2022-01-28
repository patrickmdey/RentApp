import {BaseApiSlice} from "../baseApiSlice";
import {CreateUserParameters, UpdateUserParameters, User} from "../users/types";

interface Credentials {
    email: string,
    password: string
}

const AuthApiSlice = BaseApiSlice.injectEndpoints({
    endpoints: (build) => ({

        login: build.mutation<void,Credentials> ({
           query: ({email, password}) => {
               const credentials = Buffer.from(`${email}:${password}`).toString('base64')
               console.log(credentials)
               return {
                   url: '',
                   method: "POST",
                   headers: [['Authorization','Basic ' + credentials]]
               }
           },

        }),
    })
});

export const {
    useLoginMutation
} = AuthApiSlice;