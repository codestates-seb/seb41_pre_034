import React from 'react';
import { Link } from 'react-router-dom';
import OpenIdButton from '../components/OpenIdButton';
import SignupForm from '../components/SignupForm';
import ROUTE_PATH from '../constants/routePath';

function SignUp() {
  return (
    <div className="flex justify-center mt-[50px] pt-[50px] w-full min-h-[calc(100vh-50px)] bg-[#f1f2f3]">
      <div className="flex flex-col justify-center items-center">
        <div className="flex">
          <div className="mr-[48px] mb-[128px] flex flex-col justify-center">
            <h1 className="mb-[32px] text-[27px]">
              Join the Stack Overflow community
            </h1>
            <div className="flex mb-[24px]">
              <div className="flex mr-[8px]">
                <svg width="26" height="26" fill="#0a95ff">
                  <path
                    opacity=".5"
                    d="M4.2 4H22a2 2 0 012 2v11.8a3 3 0 002-2.8V5a3 3 0 00-3-3H7a3 3 0 00-2.8 2z"
                  ></path>
                  <path d="M1 7c0-1.1.9-2 2-2h18a2 2 0 012 2v12a2 2 0 01-2 2h-2v5l-5-5H3a2 2 0 01-2-2V7zm10.6 11.3c.7 0 1.2-.5 1.2-1.2s-.5-1.2-1.2-1.2c-.6 0-1.2.4-1.2 1.2 0 .7.5 1.1 1.2 1.2zm2.2-5.4l1-.9c.3-.4.4-.9.4-1.4 0-1-.3-1.7-1-2.2-.6-.5-1.4-.7-2.4-.7-.8 0-1.4.2-2 .5-.7.5-1 1.4-1 2.8h1.9v-.1c0-.4 0-.7.2-1 .2-.4.5-.6 1-.6s.8.1 1 .4a1.3 1.3 0 010 1.8l-.4.3-1.4 1.3c-.3.4-.4 1-.4 1.6 0 0 0 .2.2.2h1.5c.2 0 .2-.1.2-.2l.1-.7.5-.7.6-.4z"></path>
                </svg>
              </div>
              <div className="text-[15px]">Get unstuck — ask a question</div>
            </div>
            <div className="flex mb-[24px]">
              <div className="mr-[8px]">
                <svg width="26" height="26" fill="#0a95ff">
                  <path d="M12 .7a2 2 0 013 0l8.5 9.6a1 1 0 01-.7 1.7H4.2a1 1 0 01-.7-1.7L12 .7z"></path>
                  <path
                    opacity=".5"
                    d="M20.6 16H6.4l7.1 8 7-8zM15 25.3a2 2 0 01-3 0l-8.5-9.6a1 1 0 01.7-1.7h18.6a1 1 0 01.7 1.7L15 25.3z"
                  ></path>
                </svg>
              </div>
              <div className="text-[15px]">
                Unlock new privileges like voting and commenting
              </div>
            </div>
            <div className="flex mb-[24px]">
              <div className="mr-[8px]">
                <svg width="26" height="26" fill="#0a95ff">
                  <path d="M14.8 3a2 2 0 00-1.4.6l-10 10a2 2 0 000 2.8l8.2 8.2c.8.8 2 .8 2.8 0l10-10c.4-.4.6-.9.6-1.4V5a2 2 0 00-2-2h-8.2zm5.2 7a2 2 0 110-4 2 2 0 010 4z"></path>
                  <path
                    opacity=".5"
                    d="M13 0a2 2 0 00-1.4.6l-10 10a2 2 0 000 2.8c.1-.2.3-.6.6-.8l10-10a2 2 0 011.4-.6h9.6a2 2 0 00-2-2H13z"
                  ></path>
                </svg>
              </div>
              <div className="text-[15px]">
                Save your favorite tags, filters, and jobs
              </div>
            </div>
            <div className="flex mb-[24px]">
              <div className="mr-[8px]">
                <svg width="26" height="26" fill="#0a95ff">
                  <path d="M21 4V2H5v2H1v5c0 2 2 4 4 4v1c0 2.5 3 4 7 4v3H7s-1.2 2.3-1.2 3h14.4c0-.6-1.2-3-1.2-3h-5v-3c4 0 7-1.5 7-4v-1c2 0 4-2 4-4V4h-4zM5 11c-1 0-2-1-2-2V6h2v5zm11.5 2.7l-3.5-2-3.5 1.9L11 9.8 7.2 7.5h4.4L13 3.8l1.4 3.7h4L15.3 10l1.4 3.7h-.1zM23 9c0 1-1 2-2 2V6h2v3z"></path>
                </svg>
              </div>
              <div className="text-[15px]">Earn reputation and badges</div>
            </div>
            <div className="text-[#6a737c] text-[13px]">
              Collaborate and share knowledge with a private group for FREE.
              <br />
              <span className="text-[#0a95ff] text-[13px]">
                Get Stack Overflow for Teams free for up to 50 users.
              </span>
            </div>
          </div>
          <div className="shrink-0 mb-[24px]">
            <div className="mb-[10px]">
              <OpenIdButton company={'google'} text={'Sign up'}></OpenIdButton>
            </div>
            <div className="mb-[10px]">
              <OpenIdButton company={'github'} text={'Sign up'}></OpenIdButton>
            </div>
            <div className="mb-[20px]">
              <OpenIdButton
                company={'facebook'}
                text={'Sign up'}
              ></OpenIdButton>
            </div>
            <SignupForm></SignupForm>
            <div className="mx-auto p-[16px] pb-[0px] mb-[24px] w-full text-center text-[13px]">
              Already have an account?
              <Link to={ROUTE_PATH.LOGIN} className="text-[#0a95ff] ml-[5px]">
                Log in
              </Link>
              <div className="mt-[12px] flex justify-center">
                Are you an employer?
                <span className="text-[#0a95ff] ml-[5px] flex items-center">
                  Sign up on Talent
                  <svg
                    className="ml-[5px]"
                    width="14"
                    height="14"
                    viewBox="0 0 14 14"
                    fill="#0a95ff"
                  >
                    <path d="M5 1H3a2 2 0 0 0-2 2v8c0 1.1.9 2 2 2h8a2 2 0 0 0 2-2V9h-2v2H3V3h2V1Zm2 0h6v6h-2V4.5L6.5 9 5 7.5 9.5 3H7V1Z"></path>
                  </svg>
                </span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default SignUp;
