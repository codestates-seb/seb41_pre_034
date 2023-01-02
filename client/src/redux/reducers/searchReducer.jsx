import { Action } from '@remix-run/router';
import React from 'react';
import { SEARCH } from '../actions/index';

function searchReducer(state = null, action) {
  switch (action.type) {
    case SEARCH:
      return action.payload;
      break;

    default:
      return state;
  }
}

export default searchReducer;
