import React from 'react';
import QuestionItem from '../components/QuestionItem';
import Footer from '../components/Footer';
import Tips from '../components/Tips';
import BlueButton from '../components/buttons/BlueButton';
import Sidebar from '../components/Sidebar';
import ROUTE_PATH from '../constants/routePath';
import Tabs from '../components/Tabs';
import useFetch from '../util/useFetch';
import PageButton from '../components/buttons/PageButton';
import { useLocation } from 'react-router-dom';
import handleAuthError from '../exception/handleAuthError';

function Home() {
  const { search } = useLocation();
  const $questions = useFetch(`/questions${search}`);

  async function handleConfirmLogin() {
    const response = await fetch(
      process.env.REACT_APP_BASE_URL + '/auth/verify',
      {
        method: 'GET',
        headers: {
          'content-type': 'application/json',
          Authorization: localStorage.getItem('Authorization'),
          Refresh: localStorage.getItem('Refresh'),
        },
      }
    );

    if (response.ok) {
      window.location.href = ROUTE_PATH.ADD_QUESTION;

      return;
    }

    handleAuthError(response.status, handleConfirmLogin);
  }

  return (
    <div className="mt-[50px] max-w-[100%] flex flex-col justify-center items-center">
      <div className="container mt-0 max-w-[1264px] flex justify-between mx-auto my-0 relative z-[1000] flex-[1_0_auto] text-left min-h-[calc(100vh-50px-322px)]">
        <div className="hidden sm:block">
          <Sidebar></Sidebar>
        </div>

        <div className="content max-w-[1100px] w-[calc(100%-164px)] p-[24px] border-l-[1px] border-[#e1e2e5]">
          <div className="flex">
            <div className="w-[753px] mb-[24px] flex flex-col">
              <div className="flex justify-between mb-[12px]">
                <span className="text-[27px] font-[500] w-[280px] h-[28.594px]">
                  All Questions
                </span>
                <div
                  className="w-[103.023px] h-[37.797px]"
                  onClick={handleConfirmLogin}
                >
                  <BlueButton text="Ask Question" />
                </div>
              </div>
              <div className="mb-[12px] flex justify-between">
                <span>{`${
                  $questions.data &&
                  $questions.data.pageInfo.totalElements.toLocaleString()
                } questions`}</span>
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
                      author={el.displayName}
                      img={el.img}
                      createAt={el.createAt}
                      updateAt={el.updateAt}
                      questionId={el.questionId}
                      questionStatus={el.questionStatus}
                      userId={el.userId}
                    />
                  ))}
                <div className="flex gap-[4px] h-[27px] my-[60px] ml-[20px]">
                  {$questions.data &&
                    new Array($questions.data.pageInfo.totalPages)
                      .fill(null)
                      .map((_, index) => {
                        return (
                          <PageButton
                            pageNumber={index + 1}
                            selected={
                              index == search.split('=')[1] ||
                              (search === '' && index === 0)
                            }
                            key={index}
                          ></PageButton>
                        );
                      })}
                </div>
              </div>
            </div>
            <div className="hidden lg:block ml-[24px] mb-[15px]">
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
