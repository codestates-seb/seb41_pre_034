import React, { useState } from 'react';
import BlueButton from './buttons/BlueButton';

function LoginForm() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  function handleEmail(e) {
    setEmail(e.target.value);
  }
  function handlePassword(e) {
    setPassword(e.target.value);
  }

  async function handleLogin(event) {
    event.preventDefault();

    const response = await fetch('/auth/login', {
      method: 'POST',
      headers: {
        'content-type': 'application/json',
      },
      body: JSON.stringify({
        username: email,
        password: password,
      }),
    });

    if (response.status === 401) {
      alert('아이디, 비밀번호를 확인해주세요.');

      return;
    }

    if (response.status === 200) {
      const authorization = response.headers.get('Authorization');
      const refresh = response.headers.get('Refresh');

      localStorage.setItem('Authorization', authorization);
      localStorage.setItem('Refresh', refresh);

      window.location.href = '/';
    }
  }

  return (
    <div className="w-[306.59px] h-[281.58px] p-[24px] rounded-[7px] shadow-lg bg-[#ffffff] flex justify-center items-center">
      <form
        onSubmit={handleLogin}
        className="flex flex-col justify-center w-[240.45px] h-[198.2px] my-[-6px]"
      >
        <div className="flex flex-col my-[6px] ">
          <label htmlFor="email" className="text-[12.6px] my-[2px] font-[600]">
            Email
          </label>

          <input
            id="email"
            type="email"
            required
            className="w-[218.445px] h-[14.994px] px-[9.1px] py-[7.8px] my-[2px] box-content text-[13px] peer/email border-[1px] border-[#babfc3] rounded-[3px] focus:outline-none focus:border-sky-500 focus:ring-1 focus:ring-sky-300 focus:shadow-md focus:shadow-sky-200"
            onChange={handleEmail}
            value={email}
            placeholder=" "
          ></input>
          <p className="hidden peer-invalid/email:block peer-placeholder-shown/email:!invisible text-[12px] text-[#de4f54] p-[2px] my-[2px]">
            Email cannot be empty
          </p>
        </div>
        <div className="flex flex-col my-[6px]">
          <label
            htmlFor="password"
            className="text-[12.6px] my-[2px] font-[600]"
          >
            Password
          </label>

          <input
            id="password"
            type="password"
            required
            className="w-[218.445px] h-[14.994px] my-[2px] px-[9.1px] py-[7.8px] text-[13px] box-content peer/password border-[1px] border-[#babfc3] rounded-[3px] focus:outline-none focus:border-sky-500 focus:ring-1 focus:ring-sky-300 focus:shadow-md focus:shadow-sky-200"
            onChange={handlePassword}
            value={password}
            placeholder=" "
          ></input>

          <p className="hidden peer-invalid/password:block peer-placeholder-shown/password:!invisible text-[12px] text-[#de4f54] p-[2px] my-[2px]">
            Password cannot be empty
          </p>
        </div>
        <div className="my-[6px]">
          <BlueButton text="Log in" className="w-[200px] h-[37.8px] my-[2px]" />
        </div>
      </form>
    </div>
  );
}

export default LoginForm;
