import React from 'react';

function Navbar(props) {
  return (
    <>
      {/* 컨테이너 */}
      <header className="box-border h-[50px] fixed left-0 top-0 min-w-[auto] shadow-md w-full z-50 bg-white	flex border-t-[3px] border-[#f48225] items-center">
        <div className="w-[97.2307692rem] max-w-full h-full flex my-0 mx-auto items-center box-border">
          {/* 로고 */}
          <a
            href="#"
            className="py-0 px-2 h-full flex items-center bg-transparent box-border hover:bg-[#e3e6e8]"
          >
            <span className="bg-no-repeat bg-left-bottom ml-0 w-[150px] h-[30px] mt-[-4px] inline-block bg-[url('https://cdn.sstatic.net/Img/unified/sprites.svg?v=fcc0ea44ba27')]"></span>
          </a>
          {/* About, Products, For Teams 버튼 */}
          <ol className="flex">
            {['About', 'Products', 'For Teams'].map((item, index) => {
              return (
                <li key={index} className="m-0 p-0">
                  <a
                    href="/"
                    className="text-[#5b6269] py-1.5 px-3 rounded-full shadow-none hover:bg-[#e3e6e8] text-sm"
                  >
                    {item}
                  </a>
                </li>
              );
            })}
          </ol>
          {/* 검색창 자리 */}
          {/* 로그인 */}
          {/* 회원가입 */}
        </div>
      </header>
    </>
  );
}

export default Navbar;
