import React from 'react';
import { AiOutlineSearch } from 'react-icons/ai';

function Inputbox() {
  return (
    <form className="w-full">
      <div className="w-[100%] border-[1px] border-[#babfc2] flex jusitfy-center items-center rounded-[3px]">
        <AiOutlineSearch className="text-[30px] text-[#929aa2] ml-1" />
        <input
          className="placeholder-[#9ea5ac] text-[15px] w-[100%] rounded-[3px] pl-[10px] pr-[6px] py-[5px] focus:outline-none focus:border-sky-500 focus:ring-1 focus:ring-sky-300 focus:shadow-md focus:shadow-sky-200"
          placeholder="Search..."
        ></input>
      </div>
    </form>
  );
}

export default Inputbox;
