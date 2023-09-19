import { createSlice } from '@reduxjs/toolkit';

const userSlice = createSlice({
  name: 'userSlice',
  initialState: { value: 0, token: null, user: null, effectNum : 0, effectMenu : null },
  reducers: {
  
  },
});

export default userSlice; 
