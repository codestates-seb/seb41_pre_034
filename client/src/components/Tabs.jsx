import React from 'react';
import { useState } from 'react';

function Tabs(props) {
  const [currentTab, setcurrentTab] = useState(0);

  const menuArr = props.menuArr;

  const selectMenuHandler = (index) => {
    setcurrentTab(index);
  };
  const submenu =
    'box-content flex p-[10.4px] mr-[-1px] mb-[-1px] hover:bg-[#f8f9f9] h-[15.794px] border-[#838C95] border-[1px]';
  const focused =
    'box-content flex p-[10.4px]  mr-[-1px] mb-[-1px] text-slate-700 bg-[#E3E6E8] h-[15.794px] border-[#838C95] border-[1px] ';
  const firstEl =
    'box-content flex p-[10.4px] mr-[-1px] mb-[-1px] hover:bg-[#f8f9f9] h-[15.794px] border-[#838C95] border-[1px] rounded-tl rounded-bl';
  const firstElfocused =
    'box-content flex p-[10.4px]  mr-[-1px] mb-[-1px] text-slate-700 bg-[#E3E6E8] h-[15.794px] border-[#838C95] border-[1px] rounded-tl rounded-bl';
  const lastEl =
    'box-content flex p-[10.4px] mr-[-1px] mb-[-1px] hover:bg-[#f8f9f9] h-[15.794px] border-[#838C95] border-[1px] rounded-tr rounded-br';
  const lastElfocused =
    'box-content flex p-[10.4px]  mr-[-1px] mb-[-1px] text-slate-700 bg-[#E3E6E8] h-[15.794px] border-[#838C95] border-[1px] rounded-tr rounded-br';
  const last = menuArr.length - 1;

  return (
    //
    <div className="box-content flex flex-wrap mb-[1px] items-center justify-start lg:justify-center text-[13px]">
      {menuArr.map((el, index) => {
        return index === 0 ? (
          <a
            key={0}
            className={currentTab === 0 ? firstElfocused : firstEl}
            onClick={() => selectMenuHandler(0)}
          >
            {el.name}
          </a>
        ) : index === last ? (
          <a
            key={last}
            className={currentTab === last ? lastElfocused : lastEl}
            onClick={() => selectMenuHandler(last)}
          >
            {el.name}
          </a>
        ) : (
          <a
            key={index}
            className={currentTab === index ? focused : submenu}
            onClick={() => selectMenuHandler(index)}
          >
            {el.name}
          </a>
        );
      })}
    </div>
  );
}

export default Tabs;
