import React from 'react';
import { Link } from 'react-router-dom';
import ROUTE_PATH from '../constants/routePath';

function Sidebar({ currentMenu = 'Home' }) {
  const menuArr = [
    { name: 'Home', link: ROUTE_PATH.HOME },
    { name: 'Tags', link: ROUTE_PATH.TAGS },
    { name: 'Users', link: ROUTE_PATH.USERS },
  ];

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
                      el.name === currentMenu
                        ? basicLiClassName +
                          ` border-r-focused bg-focused font-focused`
                        : basicLiClassName
                    }
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
