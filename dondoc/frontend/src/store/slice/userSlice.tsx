import { createSlice } from '@reduxjs/toolkit';

export type UserType ={
  phoneNumber :string;
  accessToken:string;
  isLogin:boolean;
  name:string;
  introduce:string;
  nickname:string|null;
  mainAccount:string|null;
  imageNumber:number;
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
  name:"",
  introduce:"",
  nickname:"",
  mainAccount:"",
  imageNumber:0,
}

const userSlice = createSlice({
  name: 'userSlice',
  initialState,
  reducers: {
  loginUser(state, action) {
      state.accessToken = action.payload.accessToken
      state.isLogin = true
      state.phoneNumber = action.payload.phoneNumber
      state.name = action.payload.name
      state.introduce = action.payload.introduce
      state.nickname = action.payload.nickname
      state.mainAccount = action.payload.mainAccount
      state.imageNumber = action.payload.imageNumber
    },
  changeImage(state,action){
    state.imageNumber = action.payload.imageNumber
  }
  },
});


export const {
  loginUser,changeImage
} = userSlice.actions;

export default userSlice; 
