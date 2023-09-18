import { configureStore } from '@reduxjs/toolkit';
import storage from 'redux-persist/lib/storage';
import { combineReducers } from 'redux';
import { persistReducer } from 'redux-persist';
import thunk from 'redux-thunk';

import userSlice from './userSlice';

const reducers = combineReducers({
  user: userSlice.reducer,
});

const persistConfig = {
  key: 'root',
  storage,
  whitelist: ['user', 'category'],
};

const persistedReducer = persistReducer(persistConfig, reducers);

let middleware = [thunk];

// 개발 환경일 때만 logger 미들웨어를 추가
if (process.env.NODE_ENV === 'development') {
  import('redux-logger').then((module) => {
    const loggerMiddleware = module.default;
    middleware = [...middleware, loggerMiddleware];
  });
}

const store = configureStore({
  reducer: persistedReducer,
  middleware,
});

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
