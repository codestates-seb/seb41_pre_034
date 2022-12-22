import React from 'react';
import { RiPencilFill } from 'react-icons/ri';
import { HiOutlineChatBubbleLeft } from 'react-icons/hi2';
import { DiStackoverflow } from 'react-icons/di';
import { BiSearchAlt } from 'react-icons/bi';

function Tips() {
  return (
    <div>
      <ul className="bg-[#fdf7e2] border border-[#f1e5bc] w-[298px] h-[445px] flex-col mb-[15px] shadow-sm rounded-[3px]">
        <li className="text-[#525060]  bg-[#fcf3d5] text-xs font-bold border-[#f1e5bc] border-b py-[12px] px-[15px] align-middle">
          The Overflow Blog
        </li>
        <li className="text-[#525060] text-xs flex w-[266px] h-[34px] px-[16px] my-[12px]">
          <RiPencilFill className="w-[16px] h-[16px] mr-[4px]" />
          Let's talk about our favorite terminal tools (EP. 521)
        </li>
        <li className="text-[#525060] text-xs flex w-[266px] h-[34px] px-[16px] my-[12px]">
          <RiPencilFill className="w-[16px] h-[16px] mr-[4px]" /> Best practices
          to increase the speed for Next.js apps
        </li>
        <li className="text-[#525060]  text-xs bg-[#fcf3d5] border-[#f1e5bc] font-bold border-b border-t px-[15px] py-[12px]">
          Featured on Meta
        </li>
        <li className="text-[#525060] text-xs flex w-[266px] h-[34px] px-[16px] my-[12px]">
          <HiOutlineChatBubbleLeft className="w-[20px] h-[16px] mr-[4px]" />
          Help us identify new roles for community members
        </li>
        <li className="text-[#525060] text-xs flex w-[266px] h-[34px] px-[16px] my-[12px]">
          <HiOutlineChatBubbleLeft className="w-[16px] h-[16px] mr-[4px]" />
          Navigation and UI research starting soon
        </li>
        <li className="text-[#525060]  text-xs flex w-[266px] h-[34px] px-[16px] my-[12px]">
          <DiStackoverflow className="w-[30px] h-[30px] mr-[4px]" />
          2022Comunity Moderator Election Results - now with two more mods!
        </li>
        <li className="text-[#525060]  text-xs flex w-[266px] h-[34px] px-[16px] my-[12px]">
          <DiStackoverflow className="w-[30px] h-[30px] mr-[4px]" /> Proposing a
          Community-Specific Closure Reaseon for non-English content
        </li>
        <li className="text-[#525060]  text-xs flex w-[266px] h-[34px] px-[16px] my-[12px]">
          <DiStackoverflow className="w-[30px] h-[30px] mr-[4px]" /> Temporary
          policy: ChatGPT is banned I'm standing down as a moderator
        </li>
      </ul>
      <ul className="w-[298px] h-[93.5px] border border-[#edeeef] shadow-sm mb-[15px] rounded-[3px]">
        <li className=" h-[44.5px] px-[15px] py-[12px] bg-[#f8f9f9] text-[14px] text-[#6d7379] border-b border-[#edeeef]">
          Custom Filters
        </li>
        <li className="h-[17px] my-[12px] px-[15px] bg-white text-[#0074cc] text-[13px]">
          Create a custom filter
        </li>
      </ul>
      <div className="w-[298px] h-[225px] border border-[#edeeef] shadow-sm mb-[15px] rounded-[3px]">
        <div className=" h-[44.5px] px-[15px] py-[12px] bg-[#f8f9f9] text-[14px] text-[#6d7379] border-b border-[#edeeef]">
          Watched Tags
        </div>
        <ul className="flex flex-col justify-evenly items-center">
          <li className="w-[210.664px] file:mx-[28.664px] my-[5px] flex justify-center align-middle">
            <BiSearchAlt className="w-[48px] h-[48px] my-[8px] text-[#3a749d]" />
          </li>
          <li className="h-[17px] my-[12px] px-[15px] bg-white text-[#525060] text-[13px]">
            Watch tags to curate your list of questions.
          </li>
          <div className=" bg-[#e1ecf4] text-[#3a749d] border border-[#94b7d2] w-[109.322px] h-[34.431px] p-[6px] my-[8px] text-[12px] flex justify-center align-middle hover:bg-[#b3d3ea] rounded-[3px]">
            Watch a tag
          </div>
        </ul>
      </div>
      <ul className="w-[298px] h-[110px] border border-[#edeeef] shadow-sm mb-[15px] rounded-[3px]">
        <li className=" h-[44.5px] px-[15px] py-[12px] bg-[#f8f9f9] text-[14px] text-[#6d7379] border-b border-[#edeeef]">
          Ignored Tags
        </li>
        <li className="h-[41.5px] my-[13px] px-[15px] bg-white text-[#0074cc] text-[13px]">
          <div className=" bg-[#e1ecf4] text-[#3a749d] border border-[#94b7d2] w-[128px] h-[34.431px] p-[6px] mx-[69.648px] text-[12px] flex justify-center align-middle hover:bg-[#b3d3ea] rounded-[3px]">
            Add an ignored tag
          </div>
        </li>
      </ul>
    </div>
  );
}

export default Tips;
