import { combineReducers } from 'redux';
import userIdReducer from './userIdReducer';
import { persistReducer } from 'redux-persist';
import storage from 'redux-persist/lib/storage';

const persistConfig = {
  key: 'root',
  storage,
  whitelist: ['userIdReducer'],
};

const rootReducer = combineReducers({ userIdReducer });

export default persistReducer(persistConfig, rootReducer);
