import React from 'react';
import profile from '../profile.jpeg';

function QuestionItem(props) {
  return (
    <div className="relative flex justify-center w-[719px] items-center text-[17px] p-[16px] border-b-[1px] m-5">
      <div className="flex flex-col justify-between w-[108px] h-[69px] mr-[16px] mb-[4px] text-[13px]">
        <div className="flex justify-end ">
          <span className="mr-[2px]">10</span>
          <span>vote</span>
        </div>
        <div className="flex justify-end">
          <span className="mr-[2px]">1</span>
          <span>answers</span>
        </div>
        <div className="flex justify-end">
          <span className="mr-[2px]">78</span>
          <span>views</span>
        </div>
      </div>
      <div className="flex flex-col justify-start w-[595px] h-[73px]">
        <a className="flex h-min w-max mb-[5px]">
          how can i make the lines variable in a file?
        </a>
        <div className="flex">
          <div className="flex w-min h-min">
            <ul className="flex list-none ml-0">
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
          <div className="flex justify-end ml-[auto] text-[12px]">
            <div>
              <a className="m-[2px]">
                <div>
                  <img className="w-[24px] h-[24px]" src={profile}></img>
                </div>
              </a>
            </div>
            <div className="flex items-center">
              <div>
                <a className="m-[2px]">z1nun</a>
              </div>
              <ul className="list-none ml-0">
                <li>
                  <span className="font-bold">80k</span>
                </li>
              </ul>
            </div>
            <time className="flex items-center ml-[2px]">
              modified 7 secs ago
            </time>
          </div>
        </div>
      </div>
    </div>
  );
}

export default QuestionItem;
