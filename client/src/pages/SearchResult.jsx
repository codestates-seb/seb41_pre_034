import React, { useEffect, useState } from 'react';
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
import { useSelector } from 'react-redux';

function SearchResult() {
  const { search } = useLocation();
  const searchValue = useSelector((state) => state.searchReducer);
  function setUrl(value) {
    if (/^\[/.test(value)) {
      return `/search?page=0&tag=${value.slice(1, value.length - 2)}`;
    } else if (/^user:/.test(value)) {
      return `/search?page=0&displayName=${value.slice(5)}`;
    } else {
      return `/search?page=0&keyWord=${value}`;
    }
  }
  const url = setUrl(searchValue);
  const $searchItems = useFetch(url);

  //ask question 버튼 눌렀을 때 확인 절차
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

    if (response.status >= 400 && response.status < 500) {
      handleAuthError(response.status, handleConfirmLogin);
    }
  }

  return (
    <div className="mt-[50px] max-w-[100%] flex flex-col justify-center items-center">
      <div className="container mt-0 max-w-[1264px] w-full flex justify-between mx-auto my-0 relative z-[1000] flex-[1_0_auto] text-left min-h-[calc(100vh-50px-322px)]">
        <Sidebar></Sidebar>

        <div className="content max-w-[1100px] w-[calc(100%-164px)] p-[24px] border-l-[1px] border-[#e1e2e5]">
          <div className="flex">
            <div className="w-[753px] mb-[24px] flex flex-col">
              <div className="flex justify-between mb-[12px]">
                <span className="text-[27px] font-[500] w-[280px] h-[28.594px]">
                  Search Results
                </span>
                <div
                  className="w-[103.023px] h-[37.797px]"
                  onClick={handleConfirmLogin}
                >
                  <BlueButton text="Ask Question" />
                </div>
              </div>
              <div className="mb-[12px] flex justify-between">
                <span>
                  {$searchItems.data && $searchItems.data.data.length}
                  results
                </span>
                <Tabs
                  menuArr={[
                    { name: 'Relevance' },
                    { name: 'Newest' },
                    { name: 'more' },
                  ]}
                />
              </div>
              <div className="border-t-[1px] border-[#e8eaec] ml-[-24px] mb-[20px]">
                {$searchItems.data &&
                  $searchItems.data.data.map((el, idx) => (
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
                    />
                  ))}
                <div className="flex gap-[4px] h-[27px] my-[60px] ml-[20px]">
                  {$searchItems.data &&
                    new Array($searchItems.data.pageInfo.totalPages)
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

export default SearchResult;
