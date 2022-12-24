import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import ROUTE_PATH from '../constants/routePath';

function Sidebar() {
  const [currentMenu, setCurrentMenu] = useState(0);
  // 클릭 이벤트가 발생하면 해당 li 요소로 focus가 이동한다
  const menuArr = [
    { name: 'Home', link: ROUTE_PATH.HOME },
    { name: 'Tags', link: ROUTE_PATH.TAGS },
    { name: 'Users', link: ROUTE_PATH.USERS },
  ];

  const selectMenuHandler = (index) => {
    setCurrentMenu(index);
  };

  const basicLiClassName =
    'h-[30px] py-[4px] pl-[8px] pr-[4px] text-[#525960] text-[13px] cursor-pointer w-[164px]';

  return (
    <div className="sidebar flex shrink-[0] relative w-[164px] text-left">
      <div className="sticky w-auto top-[50px] mb-[8px] max-h-[calc(100vh-50px-322px)] pt-[24px] bg-[#feffff]">
        <nav>
          <ol>
            {menuArr.map((el, idx) => {
              return (
                <Link to={el.link} key={idx}>
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
                </Link>
              );
            })}
          </ol>
        </nav>
      </div>
    </div>
  );
}

export default Sidebar;
