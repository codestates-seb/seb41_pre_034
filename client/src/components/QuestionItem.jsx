import React from 'react';
import profile from '../profile.jpeg';

function QuestionItem(props) {
  return (
    <div className="relative flex justify-center w-[719px] items-center p-[16px] border-b-[1px] m-5">
      <div className="flex flex-col gap-2 justify-start w-[108px] h-[99.859px] mr-[16px] mb-[4px] text-[13px]">
        <div className="flex justify-end gap-x-1 font-bold">
          <span>10</span>
          <span>vote</span>
        </div>
        <div className="flex justify-end gap-x-1">
          <span>1</span>
          <span>answers</span>
        </div>
        <div className="flex justify-end gap-x-1">
          <span>78</span>
          <span>views</span>
        </div>
      </div>
      <div className="flex flex-col w-[595px] h-[99.859pxpx] text-[12px] mb-[5px]">
        <h3 className="mb-[5px]">
          <a className="text-[17px] ">
            how can i make the lines variable in a file?
          </a>
        </h3>
        <div className="line-clamp-2 mb-[8px]">
          I am working on a single page application (SPA) app that grants access
          to specific paths in the application, based on roles setup in Azure AD
          for the user logging in. As per this
          https://github.com/Azure-Samples/ms-identity-javascript-react-tutorial/tree/main/5-AccessControl/1-call-api-roles
        </div>
        <div className="flex h-[38.594px]">
          <div className="flex">
            <ul className="flex list-none ml-0 mt-[4px]">
              <li className="mr-[4px]">
                <a className="border-solid border-[#e1ecf4] bg-[#e1ecf4] text-[#39739d] hover:bg-[#B3D3EA] rounded text-xs py-[4.8px] px-[6px] border-[1px] mr-[2px] mb-[2px]">
                  javascript
                </a>
              </li>
              <li className="mr-[4px]">
                <a className="border-solid border-[#e1ecf4] bg-[#e1ecf4] text-[#39739d] hover:bg-[#B3D3EA] rounded text-xs py-[4.8px] px-[6px] border-[1px] mr-[2px] mb-[2px]">
                  HTML
                </a>
              </li>
              <li className="mr-[4px]">
                <a className="border-solid border-[#e1ecf4] bg-[#e1ecf4] text-[#39739d] hover:bg-[#B3D3EA] rounded text-xs py-[4.8px] px-[6px] border-[1px] mr-[2px] mb-[2px]">
                  CSS
                </a>
              </li>
            </ul>
          </div>
          <div className="flex ml-[auto] items-center">
            <div className="flex text-[12px] gap-1 h-[16px]">
              <div>
                <a>
                  <div>
                    <img className="w-[16px] h-[16px]" src={profile}></img>
                  </div>
                </a>
              </div>
              <div className="flex">
                <div>
                  <a className="m-[2px]">z1nun</a>
                </div>
                <ul className="list-none ml-0">
                  <li>
                    <span className="font-bold">80k</span>
                  </li>
                </ul>
              </div>
              <time className="flex ml-[2px]">modified 7 secs ago</time>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default QuestionItem;
