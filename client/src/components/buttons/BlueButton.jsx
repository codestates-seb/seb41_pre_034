import React from 'react';

function BlueButton(props) {
  return (
    <div className="block">
      <a
        href="/"
        className="bg-[#0a95ff] border border-solid	rounded border-transparent text-[#ffffff] p-[10px] text-[13px] font-normal leading-4 relative hover:bg-[#39739d]"
      >
        {props.text}
      </a>
    </div>
  );
}

export default BlueButton;
