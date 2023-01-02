import { Action } from '@remix-run/router';
import React from 'react';
import { SET_USERID } from '../actions/index';
import { initialState } from './initialState';

function userIdReducer(state = null, action) {
  switch (action.type) {
    case SET_USERID:
      return action.payload;

    default:
      return state;
  }
}

export default userIdReducer;
