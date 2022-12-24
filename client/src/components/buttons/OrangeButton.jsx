import React from 'react';

function OrangeButton(props) {
  const SelectedColor =
    'font-[500] px-[12px] py-[6px] bg-[#f48225] hover:bg-[#bd5a0a] text-white rounded-2xl h-[17px] box-content ';
  const NoneselectedColor =
    'font-[500] px-[12px] py-[6px] hover:bg-[#e3e6e8] rounded-2xl h-[17px] box-content ';
  return (
    <a className={props.text === 'profile' ? SelectedColor : NoneselectedColor}>
      {props.text}
    </a>
  );
}

export default OrangeButton;
