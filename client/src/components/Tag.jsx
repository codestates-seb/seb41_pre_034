import React from 'react';

function Tag(props) {
  return (
    <a
      href="/"
      className="bg-[#e1ecf4] rounded text-[#39739d] px-1.5 py-1 text-[13px] font-normal leading-4 relative hover:bg-[#d0e3f1] hover:text-[#2c5877]"
    >
      {props.text}
    </a>
  );
}

export default Tag;
