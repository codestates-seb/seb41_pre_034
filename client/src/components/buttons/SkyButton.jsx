import React from 'react';

function SkyButton(props) {
  return (
    <div>
      <a
        href="/"
        className="inline-block text-center w-full h-full bg-[#e1ecf4] border-[1px] border-solid	rounded border-[#39739d] text-[#39739d] p-[10px] text-[13px] font-normal leading-4 relative hover:bg-[#b3d3ea]"
      >
        {props.text}
      </a>
    </div>
  );
}

export default SkyButton;
