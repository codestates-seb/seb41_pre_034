import React from 'react';
import { AiOutlineSearch } from 'react-icons/ai';

//사용시에 div 씌워서 div의 className으로 w-[]설정해주기
function Inputbox() {
  const basicInputClassname =
    'h-[20px] mt-[10px] flex px-[8px] justify-center items-center';
  return (
    <div>
      <form>
        <div className="w-[100%] border-[1px] border-[#babfc2] flex jusitfy-center items-center rounded-[3px]">
          <AiOutlineSearch className="text-[30px] text-[#929aa2]" />
          <input
            className="placeholder-[#9ea5ac] text-[15px] w-[100%] rounded-[3px] pl-[10px] pr-[6px] py-[5px] focus:outline-none focus:border-sky-500 focus:ring-1 focus:ring-sky-300 focus:shadow-md focus:shadow-sky-200"
            placeholder="Search..."
          ></input>
        </div>
      </form>
    </div>
  );
}

export default Inputbox;
