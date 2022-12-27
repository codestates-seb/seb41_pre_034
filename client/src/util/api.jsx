const BASE_URL =
  'http://ec2-54-180-142-199.ap-northeast-2.compute.amazonaws.com:8080';
// const TODO_URL = 'http://localhost:3000/todo/';

export const fetchCreate = (url, data) => {
  fetch(url, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(data),
  })
    .then(() => {
      window.location.href = BASE_URL;
    })
    .catch((error) => {
      console.error('Error', error);
    });
};

export const fetchDelete = (url, id) => {
  fetch(`${url}${id}`, {
    method: 'DELETE',
  })
    .then(() => {
      window.location.href = BASE_URL;
    })
    .catch((error) => {
      console.error('Error', error);
    });
};

export const fetchPatch = (url, id, data) => {
  fetch(`${url}${id}`, {
    method: 'PATCH',
    headers: { 'Content-Type': 'Application/json' },
    body: JSON.stringify(data),
  })
    .then(() => {
      window.location.href = `${TODO_URL}${id}`;
    })
    .catch((error) => {
      console.error('Error', error);
    });
};
