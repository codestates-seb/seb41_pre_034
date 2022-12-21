import React from 'react';

function SkyButton(props) {
  return (
    <div className="block">
      <a
        href="/"
        className="bg-[#e1ecf4] border border-solid	rounded border-transparent text-[#39739d] p-[10px] text-[13px] font-normal leading-4 relative hover:bg-[#b3d3ea]"
      >
        {props.text}
      </a>
    </div>
  );
}

export default SkyButton;
