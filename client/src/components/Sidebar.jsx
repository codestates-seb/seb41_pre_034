import React, { useState } from 'react';

function Sidebar() {
  const [currentMenu, setCurrentMenu] = useState(0);
  //클릭 이벤트가 발생하면 해당 li 요소로 focus가 이동한다
  const menuArr = [{ name: 'Home' }, { name: 'Tags' }, { name: 'Users' }];
  const selectMenuHandler = (index) => {
    setCurrentMenu(index);
  };
  const basicLiClassName =
    'h-[30px] py-[4px] pl-[8px] pr-[4px] text-[#525960] text-[13px] cursor-pointer';
  return (
    <div className="pt-[24px] mb-[8px] w-[164px] felx-col h-[100vh] relative bg-[#feffff] border-r-[1px] br-[#d7d9dc]">
      <nav className="sticky top-[50px]">
        <ol className="flex-col justify-evenly h-[200px]">
          {menuArr.map((el, idx) => {
            return (
              <li
                className={
                  idx === currentMenu
                    ? basicLiClassName +
                      ` border-r-focused bg-focused font-focused`
                    : basicLiClassName
                }
                onClick={() => selectMenuHandler(idx)}
              >
                {el.name}
              </li>
            );
          })}
          {/* <li className="py-[4px] pl-[8px] pr-[4px] text-[#525960] text-[13px] cursor-pointer">
            <a>Home</a>
          </li>
          <li>
            <li className="text-[#525960] flex pl-[30px] py-[4px] pr-[4px] text-[13px] cursor-pointer">
              <a>Tags</a>
            </li>
            <li className="text-[#525960] flex pl-[30px] py-[4px] pr-[4px] text-[13px] cursor-pointer">
              <a>Users</a>
            </li>
          </li> */}
        </ol>
      </nav>
    </div>
  );
}

export default Sidebar;
