import BASE_URL from '../constants/baseUrl';

export const fetchRead = (url) => {
  fetch(BASE_URL + url).then((res) => {});
};

export const fetchPost = async (url, data) => {
  try {
    const response = await fetch(BASE_URL + url, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization: localStorage.getItem('Authorization'),
        Refresh: localStorage.getItem('Refresh'),
      },
      body: JSON.stringify(data),
    });

    return response;
  } catch (error) {
    console.error('Error', error);
  }
};

export const fetchSign = async (url, data) => {
  try {
    const response = await fetch(BASE_URL + url, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data),
    });

    return response;
  } catch (error) {
    console.error('Error', error);
  }
};

export const fetchDelete = (url, id, redirectURL = '/') => {
  fetch(`${BASE_URL}${url}${id}`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json',
      Authorization: localStorage.getItem('Authorization'),
      Refresh: localStorage.getItem('Refresh'),
    },
  })
    .then(() => {
      window.location.href = redirectURL;
    })
    .catch((error) => {
      console.error('Error', error);
    });
};

export const fetchPatch = async (url, id, data) => {
  try {
    const response = await fetch(`${BASE_URL}${url}${id}`, {
      method: 'PATCH',
      headers: {
        'Content-Type': 'Application/json',
        Authorization: localStorage.getItem('Authorization'),
        Refresh: localStorage.getItem('Refresh'),
      },
      body: JSON.stringify(data),
    });

    return response;
  } catch (error) {
    console.error('Error', error);
  }
};
