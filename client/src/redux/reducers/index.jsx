import { combineReducers } from 'redux';
import userIdReducer from './userIdReducer';
import searchReducer from './searchReducer';
import { persistReducer } from 'redux-persist';
import storage from 'redux-persist/lib/storage';

const persistConfig = {
  key: 'root',
  storage,
  whitelist: ['userIdReducer', 'searchReducer'],
};

const rootReducer = combineReducers({ userIdReducer, searchReducer });

export default persistReducer(persistConfig, rootReducer);
