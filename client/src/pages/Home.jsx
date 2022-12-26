import React from 'react';
import QuestionItem from '../components/QuestionItem';
import Footer from '../components/Footer';
import Tips from '../components/Tips';
import BlueButton from '../components/buttons/BlueButton';
import Sidebar from '../components/Sidebar';
import ROUTE_PATH from '../constants/routePath';
import { Link } from 'react-router-dom';
import Tabs from '../components/Tabs';
import useFetch from '../util/useFetch';

function Home(props) {
  const $questions = useFetch('/questions');

  return (
    <div className="mt-[50px] max-w-[100%] flex flex-col justify-center items-center">
      <div className="container mt-0 max-w-[1264px] w-full flex justify-between mx-auto my-0 relative z-[1000] flex-[1_0_auto] text-left min-h-[calc(100vh-50px-322px)]">
        <Sidebar></Sidebar>

        <div className="content max-w-[1100px] w-[calc(100%-164px)] p-[24px] border-l-[1px] border-[#e1e2e5]">
          <div className="flex">
            <div className="w-[753px] mb-[24px] flex flex-col">
              <div className="flex justify-between mb-[12px]">
                <span className="text-[27px] font-[500] w-[280px] h-[28.594px]">
                  All Questions
                </span>
                <div className="w-[103.023px] h-[37.797px]">
                  <Link to={ROUTE_PATH.ADD_QUESTION}>
                    <BlueButton text="Ask Question" />
                  </Link>
                </div>
              </div>
              <div className="mb-[12px] flex justify-between">
                <span>23,339,958 question</span>
                <Tabs
                  menuArr={[
                    { name: 'Newest' },
                    { name: 'Active' },
                    { name: 'Bountied' },
                    { name: 'Unanswered' },
                    { name: 'more' },
                  ]}
                />
              </div>
              <div className="border-t-[1px] border-[#e8eaec] ml-[-24px] mb-[20px]">
                {$questions.data &&
                  $questions.data.data.map((el, idx) => (
                    <QuestionItem
                      key={idx}
                      vote={el.countingVote}
                      answers={el.answerCounting}
                      views={el.viewCounting}
                      title={el.title}
                      body={el.body}
                      tags={el.tags}
                      author={el.userId}
                      //프로필 이미지 받아와야함
                      img={el.img}
                      createAt={el.createAt}
                    />
                  ))}
              </div>
            </div>
            <div className="ml-[24px] mb-[15px]">
              <Tips />
            </div>
          </div>
        </div>
      </div>
      <Footer />
    </div>
  );
}

export default Home;
