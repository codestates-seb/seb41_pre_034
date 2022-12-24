import React from 'react';
import Sidebar from '../components/Sidebar';
import Footer from '../components/Footer';
import MypageContent from '../components/MypageContent';

function Mypage(props) {
  return (
    <div className="pt-[50px]">
      <div className="container mt-0 max-w-[1264px] w-full flex justify-between mx-auto my-0 relative z-[1000] flex-[1_0_auto] text-left min-h-[calc(100vh-50px-322px)]">
        <Sidebar></Sidebar>
        <div className="content max-w-[1100px] w-[calc(100%-164px)] p-[24px] border-l-[1px] border-[#e1e2e5]">
          <MypageContent />
        </div>
      </div>
      <Footer />
    </div>
  );
}

export default Mypage;
