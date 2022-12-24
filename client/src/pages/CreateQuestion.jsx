import React from 'react';
import Footer from '../components/Footer';
import MdArea from '../components/MdArea';
import BlueButton from '../components/buttons/BlueButton';

function CreateQuestion(props) {
  return (
    <div className="max-w-[100%] flex flex-col justify-center items-center ">
      <div className="mt-[50px] px-[24px] pb-[24px] max-w-[1264px]">
        <div className="mx-[16px] mb-[12px] flex justify-between">
          <span className="my-[27px] text-[27px] font-[700] w-[280px] h-[28.594px]">
            Ask a public question
          </span>
          <img
            src="https://cdn.sstatic.net/Img/ask/background.svg?v=2e9a8205b368"
            className="max-w-[600px] max-h-[130px]"
          ></img>
        </div>
        <div className="w-[70%] p-[24px] m-[16px] bg-[#ebf4fb] border-[1px] border-[#b8d7f0] rounded-[3px]">
          <p className="font-[500] text-[22px] mb-[5px]">
            Writing a good question
          </p>
          <p className="text-[15px]">
            You’re ready to ask a programming-related question and this form
            will help guide you through the process. Looking to ask a
            non-programming question? See the topics here to find a relevant
            site.
          </p>
          <div className="mt-[10px]">
            <span className="text-[13px] font-[700]">Steps</span>
            <ul className="text-[13px] list-disc ml-[20px]">
              <li className="marker:text-[#000000]">
                Summarize your problem in a one-line title.
              </li>
              <li className="marker:text-[#000000]">
                Describe your problem in more detail.
              </li>
              <li className="marker:text-[#000000]">
                Describe what you tried and what you expected to happen.
              </li>
              <li className="marker:text-[#000000]">
                Add “tags” which help surface your question to members of the
                community.
              </li>
              <li>Review your question and post it to the site.</li>
            </ul>
          </div>
        </div>
        <form className="flex flex-col justify-center item-start">
          <div className="p-[24px] m-[16px] w-[70%] flex flex-col border-[1px] border-[#e5e7e8] rounded-[3px]">
            <label htmlFor="title" className="font-[600] my-[2px]">
              Title
            </label>
            <input
              type="text"
              id="title"
              placeholder="e.g Is there an R function for finding the index of an element in a vector"
              className="border-[1px] border-[#e5e7e8] my-[2px] py-[7.8px] px-[9.1px] text-[13px] rounded-[3px] h-[32px] focus:outline-none focus:border-sky-500 focus:ring-1 focus:ring-sky-300 focus:shadow-md focus:shadow-sky-200"
            ></input>
            <div className="my-[20px]">
              <p className="font-[600] my-[4px]">
                What are the details of your problem?
              </p>
              <MdArea />
            </div>
            <label htmlFor="tags" className="font-[600] my-[2px]">
              Tags
            </label>
            <input
              id="tags"
              placeholder="e.g.(ajax wpf sql)"
              className="border-[1px] border-[#e5e7e8] my-[2px] py-[7.8px] px-[9.1px] text-[13px] rounded-[3px] h-[32px] focus:outline-none focus:border-sky-500 focus:ring-1 focus:ring-sky-300 focus:shadow-md focus:shadow-sky-200"
            ></input>
          </div>
          <div className="w-[250px] h-[60px] mt-[12px] m-[16px]  p-[10.4px]">
            <BlueButton text="Post your question" />
          </div>
        </form>
      </div>
      <Footer />
    </div>
  );
}

export default CreateQuestion;
