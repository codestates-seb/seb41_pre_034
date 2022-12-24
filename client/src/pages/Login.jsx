import React from 'react';
import { Link } from 'react-router-dom';
import LoginForm from '../components/LoginForm';
import OpenIdButton from '../components/OpenIdButton';

function Login() {
  return (
    <div className="flex justify-center items-center mt-[50px] pt-[50px] w-full min-h-[calc(100vh-50px)] bg-[#f1f2f3]">
      <div className="justify-center items-center">
        <div className="flex text-center justify-center mb-[24px] mx-auto">
          <Link to={'/'}>
            <svg width="32" height="37" viewBox="0 0 32 37">
              <path d="M26 33v-9h4v13H0V24h4v9h22Z" fill="#BCBBBB"></path>
              <path
                d="m21.5 0-2.7 2 9.9 13.3 2.7-2L21.5 0ZM26 18.4 13.3 7.8l2.1-2.5 12.7 10.6-2.1 2.5ZM9.1 15.2l15 7 1.4-3-15-7-1.4 3Zm14 10.79.68-2.95-16.1-3.35L7 23l16.1 2.99ZM23 30H7v-3h16v3Z"
                fill="#F48024"
              ></path>
            </svg>
          </Link>
        </div>
        <div className="mb-[10px]">
          <OpenIdButton company={'google'} text={'Log in'}></OpenIdButton>
        </div>
        <div className="mb-[10px]">
          <OpenIdButton company={'github'} text={'Log in'}></OpenIdButton>
        </div>
        <div className="mb-[20px]">
          <OpenIdButton company={'facebook'} text={'Log in'}></OpenIdButton>
        </div>
        <LoginForm></LoginForm>
        <div className="mx-auto fs-body1 p-[16px] pb-[0px] mb-[24px] w-full text-xs text-center mt-[24px]">
          Don’t have an account?
          <Link to={'/signup'}>
            <span className="text-[#0074cc] ml-[5px]">Sign up</span>
          </Link>
          <div className="mt-[12px] flex justify-center">
            Are you an employer?
            <Link to={'/signup'}>
              <span className="text-[#0074cc] ml-[5px]">Sign up on Talent</span>
            </Link>
            <svg
              className="ml-[5px]"
              width="14"
              height="14"
              viewBox="0 0 14 14"
              fill="#0074cc"
            >
              <path d="M5 1H3a2 2 0 0 0-2 2v8c0 1.1.9 2 2 2h8a2 2 0 0 0 2-2V9h-2v2H3V3h2V1Zm2 0h6v6h-2V4.5L6.5 9 5 7.5 9.5 3H7V1Z"></path>
            </svg>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Login;
