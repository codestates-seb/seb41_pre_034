import BASE_URL from '../constants/baseUrl';
import ROUTE_PATH from '../constants/routePath';

const handleAuthError = (status, callback) => {
  unauthorized(status);
  forbidden(status, callback);
};

const unauthorized = (status) => {
  if (status === 401) {
    alert('로그인 후 이용해주세요.');
    window.location.href = ROUTE_PATH.LOGIN;

    return;
  }
};

const forbidden = async (status, callback) => {
  if (status === 403) {
    const response = await fetch(BASE_URL + '/auth/reissuetoken');

    if (!response.ok) {
      alert('로그인 후 이용해주세요.');
      window.location.href = ROUTE_PATH.LOGIN;

      return;
    }

    const authorization = response.headers.get('Authorization');
    const refresh = response.headers.get('Refresh');

    localStorage.setItem('Authorization', authorization);
    localStorage.setItem('Refresh', refresh);

    callback();
  }
};

export default handleAuthError;
