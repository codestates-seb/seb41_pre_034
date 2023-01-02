export const SET_USERID = 'SET_USERID';
export const SEARCH = 'SEARCH';

export const setUserId = (userId) => {
  return {
    type: SET_USERID,
    payload: userId,
  };
};

export const search = (searchValue) => {
  return {
    type: SEARCH,
    payload: searchValue,
  };
};
