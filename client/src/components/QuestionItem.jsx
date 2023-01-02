import React from 'react';
import basicProfile from '../assets/basicProfile.png';
import ROUTE_PATH from '../constants/routePath';
import { timeForToday } from '../util/timeForToday';
import { Link } from 'react-router-dom';

function QuestionItem(props) {
  return (
    <div className="relative flex flex-col lg:flex-row items-start p-[16px] border-b-[1px] border-[#e8eaec] my-5">
      <div className="flex lg:flex-col gap-2 justify-start lg:w-[108px] lg:h-[99.859px] mr-[16px] mb-[4px] text-[13px] items-end">
        <div className="flex justify-end font-bold gap-x-1">
          <span className="font-[700]">{props.vote}</span>
          <span>vote</span>
        </div>

        {props.questionStatus === 'CLOSED' ? (
          <div
            className={
              'py-[1px] flex justify-end gap-x-1 items-center bg-[#2f6f44] border-[1px] border-[#2f6f44] text-[#ffffff] rounded px-[5px] font-[600]'
            }
          >
            <svg
              aria-hidden="true"
              className="svg-icon iconCheckmarkSm"
              width="14"
              height="14"
              viewBox="0 0 14 14"
            >
              <path
                d="M13 3.41 11.59 2 5 8.59 2.41 6 1 7.41l4 4 8-8Z"
                fill="#ffffff"
              ></path>
            </svg>
            <span className="font-[600]">{props.answers}</span>
            <span>answers</span>
          </div>
        ) : props.answers > 0 ? (
          <div
            className={
              'flex justify-end gap-x-1 border-[1px] border-[#2f6f44] text-[#2f6f44] rounded px-[5px]'
            }
          >
            <span className="font-[600]">{props.answers}</span>
            <span>answers</span>
          </div>
        ) : (
          <div className={'flex justify-end gap-x-1'}>
            <span className="font-[600]">{props.answers}</span>
            <span>answers</span>
          </div>
        )}
        <div className="flex justify-end gap-x-1">
          <span className="font-[600]">{props.views}</span>
          <span>views</span>
        </div>
      </div>
      <div className="flex flex-col max-sm:w-[460px] w-full h-[99.859px] text-[12px] mb-[5px]">
        <h3 className="mb-[5px]">
          <Link
            to={`${ROUTE_PATH.DETAIL}/${props.questionId}`}
            className="text-[17px] font-[600] text-[#0074cc] hover:text-[#0a95ff]"
          >
            {props.title}
          </Link>
        </h3>
        <div className="line-clamp-2 mb-[8px]">{props.body}</div>
        <div className="flex h-[38.594px] justify-between">
          <div className="flex">
            <ul className="flex list-none ml-0 mt-[4px]">
              {props.tags.map((el, idx) => {
                return el === '' ? null : (
                  <li key={idx} className="mr-[4px]">
                    <a className="border-solid border-[#e1ecf4] bg-[#e1ecf4] text-[#39739d] hover:bg-[#B3D3EA] rounded text-xs py-[4.8px] px-[6px] border-[1px] mr-[2px] mb-[2px]">
                      {el}
                    </a>
                  </li>
                );
              })}
            </ul>
          </div>
          <div className="flex ml-[auto] items-center">
            <div className="flex text-[12px] gap-1 h-[16px]">
              <div>
                <a>
                  <div>
                    <img className="w-[16px] h-[16px]" src={basicProfile}></img>
                  </div>
                </a>
              </div>
              <div className="flex">
                <div>
                  <a className="m-[2px] font-[500] text-[#0074cc] hover:text-[#0a95ff]">
                    {props.author}
                  </a>
                </div>
              </div>
              <time className="flex ml-[2px]">
                {props.createAt === String(props.updateAt).slice(0, 19)
                  ? `asked ${timeForToday(props.createAt)}`
                  : `modified ${timeForToday(props.updateAt)}`}
              </time>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default QuestionItem;
