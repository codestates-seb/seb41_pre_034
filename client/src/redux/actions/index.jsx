export const SET_USERID = 'SET_USERID';
export const SEARCH = 'SEARCH';

//Login header에서 userid를 가져오는 행동
export const setUserId = (userId) => {
  return {
    type: SET_USERID,
    payload: userId,
  };
};

//검색창에서 검색값을 가져오는 행동
export const search = (searchValue) => {
  return {
    type: SEARCH,
    payload: searchValue,
  };
};
