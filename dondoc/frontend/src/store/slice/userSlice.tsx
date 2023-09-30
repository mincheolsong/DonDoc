import { createSlice } from '@reduxjs/toolkit';

export type UserType ={
  phoneNumber :string;
  accessToken:string;
  isLogin:boolean
}

export type Account = {
  accountId:number,
  accountName:string,
  accountNumber:string,
  balance:number,
  bankCode :string,
  bankName:string,
}

export type CheckAccount = Account &{
  isCheck: boolean
}



const initialState:UserType = {
  phoneNumber:"",
  accessToken:"",
  isLogin:false,

}

const userSlice = createSlice({
  name: 'userSlice',
  initialState,
  reducers: {
  loginUser(state, action) {
      state.accessToken = action.payload.accessToken
      state.isLogin = true
      state.phoneNumber = action.payload.phoneNumber
    }
  },
});


export const {
  loginUser
} = userSlice.actions;

export default userSlice; 
