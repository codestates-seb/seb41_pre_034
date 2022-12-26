import React from 'react';
import profile from '../assets/profile.jpeg';
import useFetch from '../util/useFetch';

function QuestionItem(props) {
  //props.tags가 string이기 때문에 map이안됨
  const tags = props.tags.split(',');
  console.log(props);
  return (
    <div className="relative flex justify-center w-[719px] items-center p-[16px] border-b-[1px] border-[#e8eaec] my-5">
      <div className="flex flex-col gap-2 justify-start w-[108px] h-[99.859px] mr-[16px] mb-[4px] text-[13px]">
        <div className="flex justify-end font-bold gap-x-1">
          <span>{props.vote}</span>
          <span>vote</span>
        </div>
        <div className="flex justify-end gap-x-1">
          <span>{props.answers}</span>
          <span>answers</span>
        </div>
        <div className="flex justify-end gap-x-1">
          <span>{props.views}</span>
          <span>views</span>
        </div>
      </div>
      <div className="flex flex-col w-[595px] h-[99.859pxpx] text-[12px] mb-[5px]">
        <h3 className="mb-[5px]">
          <a className="text-[17px] ">{props.title}</a>
        </h3>
        <div className="line-clamp-2 mb-[8px]">{props.body}</div>
        <div className="flex h-[38.594px]">
          <div className="flex">
            <ul className="flex list-none ml-0 mt-[4px]">
              {tags.map((el, idx) => (
                <li key={idx} className="mr-[4px]">
                  <a className="border-solid border-[#e1ecf4] bg-[#e1ecf4] text-[#39739d] hover:bg-[#B3D3EA] rounded text-xs py-[4.8px] px-[6px] border-[1px] mr-[2px] mb-[2px]">
                    {el}
                  </a>
                  s
                </li>
              ))}
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
                  <a className="m-[2px]">{props.author}</a>
                </div>
                <ul className="ml-0 list-none">
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
