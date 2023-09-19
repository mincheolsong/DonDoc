import { configureStore,combineReducers} from "@reduxjs/toolkit";
import { persistReducer, FLUSH, REHYDRATE, PAUSE, PERSIST, PURGE, REGISTER } from 'redux-persist';
import storage from 'redux-persist/lib/storage'
import userSlice from "./userSlice";


const persistConfig = {
  key: "root", // localStorage key 
  storage, // session
  whitelist: ["user"] // target (reducer name)
}

const rootReducer = combineReducers({
 user : userSlice.reducer
});

const persistedReducer = persistReducer(persistConfig, rootReducer);


const store = configureStore({
  reducer: persistedReducer,
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({
      serializableCheck: {
        ignoredActions: [FLUSH, REHYDRATE, PAUSE, PERSIST, PURGE, REGISTER],
      },
    }),
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
export default store;

// import {configureStore} from '@reduxjs/toolkit';
// import storage from 'redux-persist/lib/storage';
// import { combineReducers } from 'redux'
// import { persistReducer } from 'redux-persist'
// import thunk from 'redux-thunk';
// import logger from 'redux-logger';

// import userSlice from './userSlice';
// import categorySlice from './categorySlice';

// const reducers = combineReducers({
//     user: userSlice.reducer,
//     category: categorySlice.reducer
// })

// const persistConfig = {
//     key: 'root',
//     storage,
//     whitelist: ['user', 'category']
// };

// const persistedReducer = persistReducer(persistConfig, reducers);

// const store = configureStore({
//     reducer: persistedReducer,
//     middleware: [thunk, logger],
// });

// export default store;
