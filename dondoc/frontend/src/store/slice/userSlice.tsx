import { createSlice } from '@reduxjs/toolkit';

export type Socket = {
  socket: WebSocket | null;
}

export type UserType ={
  userId: number,
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

const initialState:Socket = {
  socket: null,
}


const initialState:UserType = {
  userId: 0,
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
    changeImage(state, action) {
      state.imageNumber = action.payload.imageNumber
    },
    changeIntroduce(state, action) {
      state.introduce = action.payload.introduce
    },
    changeNickName(state, action) {
      state.nickname = action.payload.nickname
    },
    changeMainAccount(state, action) {
      state.mainAccount = action.payload.mainAccount
    }
    ,
    changeSocket(state, action) {
      state.socket = action.payload.socket;
    },
  
  }
});


export const {
  loginUser,changeImage,changeIntroduce,changeNickName,changeMainAccount
} = userSlice.actions;

export default userSlice; 
