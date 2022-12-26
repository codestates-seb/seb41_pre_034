import React from 'react';
import QuestionItem from '../components/QuestionItem';
import Footer from '../components/Footer';
import Tips from '../components/Tips';
import BlueButton from '../components/buttons/BlueButton';
import Sidebar from '../components/Sidebar';
import ROUTE_PATH from '../constants/routePath';
import { Link } from 'react-router-dom';
import Tabs from '../components/Tabs';

function Home(props) {
  const example = [
    {
      id: 0,
      vote: 10,
      answers: 1,
      views: 78,
      title: 'how can i make the lines variable in a file?',
      text: 'I am working on a single page application (SPA) app that grants access to specific paths in the application, based on roles setup in Azure AD for the user logging in.',
      tags: ['javascript', 'css', 'HTML'],
      author: 'z1nun',
      img: '이미지 경로 들어갈 자리',
      createAt: '게시글이 작성된 시간 들어갈 자리',
    },
    {
      id: 0,
      vote: 10,
      answers: 1,
      views: 78,
      title: 'how can i make the lines variable in a file?',
      text: 'I am working on a single page application (SPA) app that grants access to specific paths in the application, based on roles setup in Azure AD for the user logging in.',
      tags: ['javascript', 'css', 'HTML'],
      author: 'z1nun',
      img: '이미지 경로 들어갈 자리',
      createAt: '게시글이 작성된 시간 들어갈 자리',
    },
    {
      id: 0,
      vote: 10,
      answers: 1,
      views: 78,
      title: 'how can i make the lines variable in a file?',
      text: 'I am working on a single page application (SPA) app that grants access to specific paths in the application, based on roles setup in Azure AD for the user logging in.',
      tags: ['javascript', 'css', 'HTML'],
      author: 'z1nun',
      img: '이미지 경로 들어갈 자리',
      createAt: '게시글이 작성된 시간 들어갈 자리',
    },
    {
      id: 0,
      vote: 10,
      answers: 1,
      views: 78,
      title: 'how can i make the lines variable in a file?',
      text: 'I am working on a single page application (SPA) app that grants access to specific paths in the application, based on roles setup in Azure AD for the user logging in.',
      tags: ['javascript', 'css', 'HTML'],
      author: 'z1nun',
      img: '이미지 경로 들어갈 자리',
      createAt: '게시글이 작성된 시간 들어갈 자리',
    },
  ];

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
                {example.map((el, idx) => (
                  <QuestionItem
                    key={idx}
                    vote={el.vote}
                    answers={el.answers}
                    views={el.views}
                    title={el.title}
                    text={el.text}
                    tags={el.tags}
                    author={el.author}
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
