import React from 'react';
import Sidebar from '../components/Sidebar';
import Footer from '../components/Footer';
import { Link } from 'react-router-dom';
import ROUTE_PATH from '../constants/routePath';
import BlueButton from '../components/buttons/BlueButton';

function QuestionDetail() {
  return (
    <>
      <div
        id="container"
        className="mt-[50px] max-w-[1264px] w-full flex justify-between mx-auto my-0 relative z-[1000] flex-[1_0_auto] text-left min-h-[calc(100vh-50px-322px)]"
      >
        <Sidebar></Sidebar>
        <div
          id="content"
          className="max-w-[1100px] w-[calc(100%-164px)] p-[24px] border-l-[1px] border-[#e1e2e5]"
        >
          <div id="question-header" className="flex text-[27px] items-center">
            <h1 className="w-full">{'Question Header'}</h1>
            <Link to={ROUTE_PATH.ADD_QUESTION}>
              <div className="ml-[12px] min-w-[103px]">
                <BlueButton text={'Ask Question'}></BlueButton>
              </div>
            </Link>
          </div>
          <div className="flex pb-[8px] mb-[16px] text-[13px] pb-[8px] mb-[16px] border-b-[1px] border-[#e4e6e8]">
            <div className="mr-[16px] mb-[8px]">
              <span className="mr-[6px] text-[#6a737c]">Asked</span>
              <time>today</time>
            </div>
            <div className="mr-[16px] mb-[8px]">
              <span className="mr-[6px] text-[#6a737c]">Modified</span>
              <time>today</time>
            </div>
            <div className="mr-[16px] mb-[8px]">
              <span className="mr-[6px] text-[#6a737c]">Viewed</span>
              <time>{'1000'} times</time>
            </div>
          </div>
          <div id="mainbar" className="w-[calc(100%-324px)] float-left"></div>
        </div>
      </div>
      <Footer />
    </>
  );
}

export default QuestionDetail;
