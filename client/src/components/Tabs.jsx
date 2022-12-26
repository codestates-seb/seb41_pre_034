import React from 'react';
import { useState } from 'react';

function Tabs(props) {
  const [currentTab, setcurrentTab] = useState(0);

  //users나 Home에서 배열의 length가 다를텐데 props로 배열들을 어떻게 받아줘야하는지.
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
  // border-radius관련, 부모가 div 태그이고, 자식으로 a태그들이 있는데 a태그에 각각 border-radius가 적용되있는 것이아니라 div태그에 border-radius가 있음
  // border-[#838C95] border-[1px]
  const last = menuArr.length - 1;

  return (
    //
    <div className="box-content flex flex-wrap mb-[1px] items-center justify-center text-[13px]">
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

/*
import React from 'react';
import { useState } from 'react';

function Tabs(props) {
  const [currentTab, setcurrentTab] = useState(0);

  //users나 Home에서 배열의 length가 다를텐데 props로 배열들을 어떻게 받아줘야하는지.
  const menuArr = [{ name: 'Popular' }, { name: 'Name' }, { name: 'New' }];

  const selectMenuHandler = (index) => {
    setcurrentTab(index);
  };
  const submenu =
    'box-content flex p-[10.4px] mr-[-1px] mb-[-1px] hover:bg-[#f8f9f9] h-[15.794px] border-[#838C95] border-[1px]';
  const focused =
    'box-content flex p-[10.4px]  mr-[-1px] mb-[-1px] text-slate-700 bg-[#E3E6E8] h-[15.794px] border-[#838C95] border-[1px] ';
  // border-radius관련, 부모가 div 태그이고, 자식으로 a태그들이 있는데 a태그에 각각 border-radius가 적용되있는 것이아니라 div태그에 border-radius가 있음
  // border-[#838C95] border-[1px]
  return (
    <div className="box-content flex flex-wrap mb-[1px] items-center justify-center text-[13px] border-[#838C95] border-[1px] h-[37.594px] rounded">
      {menuArr.map((el, index) => {
        return (
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
*/
