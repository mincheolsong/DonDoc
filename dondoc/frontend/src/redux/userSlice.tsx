import { createSlice } from '@reduxjs/toolkit';

const userSlice = createSlice({
  name: 'userSlice',
  initialState: { value: 0, token: null, user: null, effectNum : 0, effectMenu : null },
  reducers: {
    up: (state, action) => {
      state.value = state.value + action.payload;
    },
    setToken: (state, action) => {
      state.token = action.payload; // 토큰 값을 업데이트하는 액션 추가
    },
    setUser: (state, action) => {
      state.user = action.payload;
    },
    logOut: (state) => {
      state.token = null
      state.user = null;
    },
    profileChange: (state, action) => {
      if(state.user) {
        // console.log('profile change!', action)
        state.user['profilePicture'] = action.payload;
      }
    },
    nicknameChange: (state, action) => {
      if(state.user) {
        // console.log('nickname change!', action)
        state.user['nickname'] = action.payload;
      }
    },
    setEffectNum: (state,action) => {
      if(state.user){
        state.effectNum = action.payload;
      }
    },
    setEffectMenu: (state,action) => {
      if(state.user){
        state.effectMenu = action.payload
      }
    }
  },
});

export const { up, setToken, setUser, logOut, profileChange, nicknameChange, setEffectNum, setEffectMenu } = userSlice.actions;
export default userSlice;
