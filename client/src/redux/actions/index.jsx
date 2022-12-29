export const SET_USERID = 'SET_USERID';

//Login header에서 userid를 가져오는 행동
export const setUserId = (userId) => {
  return {
    type: SET_USERID,
    payload: userId,
  };
};
