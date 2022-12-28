import React from 'react';
import Sidebar from '../components/Sidebar';
import Footer from '../components/Footer';
import { AiOutlineSearch } from 'react-icons/ai';
import Tabs from '../components/Tabs';
import TagCard from '../components/TagCard';

function Tags(props) {
  return (
    <div className="pt-[50px]">
      <div className="container mt-0 max-w-[1264px] w-full flex justify-between mx-auto my-0 relative z-[1000] flex-[1_0_auto] text-left min-h-[calc(100vh-50px-322px)]">
        <Sidebar currentMenu="Tags"></Sidebar>
        <div className="content max-w-[1100px] w-[calc(100%-164px)] p-[24px] border-l-[1px] border-[#e1e2e5]">
          <h1 className="mb-[16px] text-[27px]">Tags</h1>
          <p className="text-[15px] mb-[16px] w-[632px] h-[39.219px]">
            A tag is a keyword or label that categorizes your question with
            other, similar questions. Using the right tags makes it easier for
            others to find and answer your question.
          </p>
          <div className="flex flex-wrap items-center m-[-2px] mb-[24px] ">
            <a
              className="text-[13px] text-[#0976CC] hover:text-[#4393f7]"
              href=""
            >
              Show all tag synonyms
            </a>
          </div>
          <div className="flex flex-wrap">
            <div className="relative mb-[12px]">
              <input
                type="search"
                className="py-[7.8px] pl-[32px] pr-[9.1px] border-[1px] rounded-[3px] "
                placeholder="Filter by tag name"
                autoFocus
                maxLength={35}
              ></input>
              <AiOutlineSearch className="w-[28px] h-[28px] absolute left-2 top-2 pr-[4px] text-[#838c95]" />
            </div>
            <div className="ml-[auto] mb-[12px] flex">
              <Tabs
                menuArr={[
                  { name: 'Popular' },
                  { name: 'Name' },
                  { name: 'New' },
                ]}
              />
            </div>
          </div>
          <div className="grid grid-cols-4 gap-4 ">
            <TagCard tag="javascript" />
            <TagCard tag="ajax" />
            <TagCard tag="css" />
            <TagCard tag="HTML" />
            <TagCard tag="Redux" />
            <TagCard tag="React" />
            <TagCard tag="tailwind" />
            <TagCard tag="python" />
            <TagCard tag="C" />
            <TagCard tag="git" />
            <TagCard tag="project" />
            <TagCard tag="code-seb" />
            <TagCard tag="FE41" />
            <TagCard tag="frontend" />
          </div>
        </div>
      </div>
      <Footer />
    </div>
  );
}

export default Tags;
