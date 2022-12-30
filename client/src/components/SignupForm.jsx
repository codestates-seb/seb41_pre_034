import React from 'react';
import { useState } from 'react';
import ImNotARobot from '../assets/ImNotARobot.png';
import BlueButton from './buttons/BlueButton';
import { RiQuestionFill } from 'react-icons/ri';
import { fetchSign as fetchSignup } from '../util/api';

function SignupForm() {
  const [displayName, setDisplayName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [isCheck, setCheck] = useState(false);

  function handleDispalyName(e) {
    setDisplayName(e.target.value);
  }

  function handleEmail(e) {
    setEmail(e.target.value);
  }

  function handlePassword(e) {
    setPassword(e.target.value);
  }

  function handleCheckboxClick() {
    setCheck(!isCheck);
  }

  function handleSubmit(e) {
    e.preventDefault();

    const data = {
      email: email,
      password: password,
      displayName: displayName,
      emailNotice: isCheck,
    };

    fetchSignup('/users', data);
    alert('회원가입이 완료되었습니다.');

    window.location.href = '/login';
  }

  return (
    <div>
      <div className="box-content rounded-[7px] shadow-lg bg-[#ffffff] w-[268px] h-[610.406px] p-[24px] mb-[24px]">
        <form onSubmit={handleSubmit} className="flex flex-col">
          <div className="flex flex-col w-[268px] h-[60.203px] my-[6px]">
            <label htmlFor="name" className="px-[2px] my-[2px] font-[700] ">
              Display name
            </label>
            <div>
              <input
                id="name"
                type="name"
                required
                className="box-content border-[1px] border-[#babfc3] rounded-[3px] w-[247.8px] h-[16px] py-[7.8px] px-[9.1px]"
                value={displayName}
                onChange={handleDispalyName}
              ></input>
            </div>
          </div>
          <div className="flex flex-col w-[268px] h-[60.203px] my-[6px]">
            <label htmlFor="email" className="font-[700] px-[2px] my-[2px]">
              Email
            </label>
            <div>
              <input
                id="email"
                type="email"
                required
                value={email}
                onChange={handleEmail}
                className="box-content border-[1px] border-[#babfc3] rounded-[3px] w-[247.8px] h-[16px] py-[7.8px] px-[9.1px] peer/email"
                placeholder=" "
              ></input>
              <p className="hidden peer-invalid/email:block peer-placeholder-shown/email:!invisible text-pink-600 text-sm p-[2px] my-[2px]">
                Please provide a valid email address.
              </p>
            </div>
          </div>
          <div className="flex flex-col w-[268px] h-[60.203px] my-[6px]">
            <label htmlFor="Password" className="font-[700] px-[2px] my-[2px]">
              Password
            </label>
            <div>
              <input
                id="Password"
                type="password"
                required
                className="peer/password box-content border-[1px] border-[#babfc3] rounded-[3px] w-[247.8px] h-[16px] py-[7.8px] px-[9.1px]"
                placeholder=" "
                onChange={handlePassword}
              ></input>
              <p className="hidden peer-invalid/password:block peer-placeholder-shown/password:!invisible text-pink-600 text-sm p-[2px] my-[2px]">
                Please provide a valid password
              </p>
            </div>
          </div>
          <p className="text-[12px] text-[#6A737C] w-[268px] h-[47.063px] my-[4px]">
            Passwords must contain at least eight characters, including at least
            1 letter and 1 number.
          </p>
          <div>
            <img
              src={ImNotARobot}
              className="box-content w-[266px] h-[144px] pt-[8px] pb-[2px] my-[9px]"
            />
          </div>
          <div className="flex items-start h-[64.750px]">
            <div className="relative">
              <input
                type="checkbox"
                className="w-[13px] mr-[4px]"
                onChange={handleCheckboxClick}
              ></input>
            </div>
            <div className="text-[12px] ">
              Opt-in to receive occasional product updates, user research
              invitations, company announcements, and digests.
            </div>
            <div>
              <RiQuestionFill className="w-[14px] mt-[2px] ml-[4px]" />
            </div>
          </div>

          <BlueButton text="Sign up" className=""></BlueButton>
        </form>
        <p className="text-[12px] w-[268px] h-[31.375px] mt-[32px] text-[#6A737C] ">
          By clicking “Sign up”, you agree to our
          <a href="/" className="text-[#3888D4]">
            terms of service, privacy policy
          </a>
          and
          <a href="/" className="text-[#3888D4]">
            cookie policy
          </a>
        </p>
      </div>
    </div>
  );
}

export default SignupForm;
