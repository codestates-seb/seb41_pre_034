import React from 'react';

function PageButton({ pageNumber, selected }) {
  return (
    <a
      href={`questions?page=${pageNumber - 1}`}
      className={`flex items-center text-center border-[1px] border-solid px-[8px] py-[5px] rounded border-transparent text-[13px] leading-[25px] ${
        selected
          ? 'text-[#ffffff] bg-[#f48225] border-[#f7a15c]'
          : 'hover:bg-[#d6d9dc] text-[#3b4045] bg-[#ffffff] border-[#dbdee1]'
      }`}
    >
      {pageNumber}
    </a>
  );
}

export default PageButton;
