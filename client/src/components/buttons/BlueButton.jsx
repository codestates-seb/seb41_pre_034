import React from 'react';

function BlueButton(props) {
  return (
    <button className="inline-block text-center w-full h-full bg-[#0a95ff] border-[1px] border-solid border-[#0a95ff] p-[10px] rounded border-transparent text-[#ffffff] text-[13px] font-normal leading-4 relative hover:bg-[#39739d]">
      {props.text}
    </button>
  );
}

export default BlueButton;
