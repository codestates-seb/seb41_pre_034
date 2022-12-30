import React from 'react';
import Sidebar from '../components/Sidebar';
import Footer from '../components/Footer';
import { Link, useParams } from 'react-router-dom';
import ROUTE_PATH from '../constants/routePath';
import BlueButton from '../components/buttons/BlueButton';
import Tag from '../components/Tag';
import { useState } from 'react';
import MdArea from '../components/MdArea';
import Tips from '../components/Tips';
import useFetch from '../util/useFetch';
import { timeForToday } from '../util/timeForToday';
import MDEditor from '@uiw/react-md-editor';
import { fetchDelete, fetchPatch, fetchPost } from '../util/api';
import { useSelector } from 'react-redux';
import basicProfile from '../assets/basicProfile.png';
import CommentTextArea from '../components/CommentTextArea';

function AddComment({ questionId, answerId }) {
  const [isOpenCommentInput, setIsOpenCommentInput] = useState(false);

  return (
    <>
      <span
        className="text-[#b5bac0] cursor-pointer text-[13px]"
        onClick={() => {
          setIsOpenCommentInput(!isOpenCommentInput);
        }}
      >
        Add a comment
      </span>
      {isOpenCommentInput ? (
        <div className="mt-[20px]">
          <CommentTextArea questionId={questionId} answerId={answerId} />
        </div>
      ) : null}
    </>
  );
}

function QuestionDetail() {
  const { questionId } = useParams();
  const $fetchData = useFetch(`/questions/${questionId}`);
  const voteIconColor = '#babfc5';
  const [answer, setAnswer] = useState('');
  const userId = Number(useSelector((state) => state.userIdReducer));
  const isCheckedQuestion =
    $fetchData.data &&
    $fetchData.data.data.answers.some(({ check }) => check === true);

  function deleteQuestion() {
    if (confirm('삭제하시겠습니까?')) {
      fetchDelete('/questions/', questionId);
    }
  }

  function addAnswer() {
    const data = {
      userId,
      questionId,
      body: answer,
    };
    fetchPost('/answers', data);
  }

  function deleteAnswer({ target }) {
    if (confirm('답변을 삭제하시겠습니까?')) {
      fetchDelete('/answers/', target.dataset.answerid, '');
    }
  }

  async function selectAnswer({ target }) {
    if (confirm('답변을 채택하시겠습니까?')) {
      const data = {
        userId,
        questionId,
        check: true,
      };
      const response = await fetchPatch(
        '/answers/',
        target.dataset.answerid,
        data
      );

      if (response.ok) {
        window.location.href = '';
      }
    }
  }

  async function handleVoteButtonClick({ target }) {
    const voteStatus = target.dataset.votetype;
    const answerId = Number(target.dataset.answerid);
    const response = await postVote(voteStatus, answerId);

    if (response.status === 409) {
      const [response, data] = await patchVote(voteStatus, answerId);

      if (response.status === 204) {
        alert('한번만 추천하거나 비추천할 수 있습니다.');
        window.location.reload();

        return;
      }

      if (response.ok && data.data.voteStatus === 'NONE') {
        alert('추천이 취소되었습니다.');
        window.location.reload();

        return;
      }
    }

    if (voteStatus === 'UP') {
      alert('해당 글을 추천 하였습니다. :)');
      window.location.reload();

      return;
    }

    if (voteStatus === 'DOWN') {
      alert('해당 글을 비추천 하였습니다. :(');
      window.location.reload();

      return;
    }
  }

  async function postVote(voteStatus, answerId) {
    const url = answerId
      ? `/answer-vote/${answerId}`
      : `/question-vote/${questionId}`;
    const data = {
      userId,
      voteStatus,
    };

    return await fetchPost(url, data);
  }

  async function patchVote(voteStatus, answerId) {
    const { questionVoteId } =
      $fetchData.data.data.questionVotes.find(
        (vote) => vote.userId === userId
      ) ?? '';

    const { answerVoteId } =
      $fetchData.data.data.answers
        .find((answer) => answer.answerId === answerId)
        ?.answerVotes.find((answer) => answer.userId === userId) ?? '';

    const url = answerId ? '/answer-vote/vote/' : '/question-vote/vote/';
    const id = answerId ? answerVoteId : questionVoteId;

    const bodyData = {
      userId,
      voteStatus,
    };
    const response = await fetchPatch(url, id, bodyData);

    let data;
    if (response.status !== 204) {
      data = await response.json();
    }

    return [response, data];
  }

  return (
    <>
      <div
        id="container"
        className="mt-[50px] max-w-[1264px] w-full flex justify-between mx-auto my-0 relative z-[1000] flex-[1_0_auto] text-left min-h-[calc(100vh-50px-322px)]"
      >
        <Sidebar></Sidebar>
        <div
          id="content"
          className="max-w-[1100px] w-[calc(100%-164px)] p-[24px] border-l-[1px] border-[#e1e2e5]"
        >
          {$fetchData.isPending ? (
            <img src="https://github.githubassets.com/images/mona-loading-dark.gif"></img>
          ) : null}
          <div id="question-header" className="flex text-[27px] items-center">
            <h1 className="w-full">
              {$fetchData.data && $fetchData.data.data.title}
            </h1>
            <Link to={ROUTE_PATH.ADD_QUESTION}>
              <div className="ml-[12px] min-w-[103px]">
                <BlueButton text={'Ask Question'}></BlueButton>
              </div>
            </Link>
          </div>
          <div className="flex text-[13px] pb-[8px] mb-[16px] border-b-[1px] border-[#e4e6e8]">
            <div className="mr-[16px] mb-[8px]">
              <span className="mr-[6px] text-[#6a737c]">Asked</span>
              <time>
                {$fetchData.data && timeForToday($fetchData.data.data.createAt)}
              </time>
            </div>
            <div className="mr-[16px] mb-[8px]">
              <span className="mr-[6px] text-[#6a737c]">Modified</span>
              <time>
                {$fetchData.data && timeForToday($fetchData.data.data.updateAt)}
              </time>
            </div>
            <div className="mr-[16px] mb-[8px]">
              <span className="mr-[6px] text-[#6a737c]">Viewed</span>
              <time>
                {$fetchData.data &&
                  $fetchData.data.data.viewCounting + ' times'}
              </time>
            </div>
          </div>
          <div className="flex">
            <div id="mainbar" className="w-[calc(100%-324px)] float-left">
              <div id="question" className="flex text-left">
                <div id="question-layout" className="flex w-full">
                  <div
                    id="question-votecell"
                    className="w-auto pr-[16px] align-top"
                  >
                    <div
                      id="question-voting-container"
                      className="flex items-stretch justify-center flex-col"
                    >
                      <button
                        id="question-up-button"
                        className="m-[2px]"
                        onClick={handleVoteButtonClick}
                      >
                        <svg
                          width="36"
                          height="36"
                          viewBox="0 0 36 36"
                          fill={voteIconColor}
                          data-votetype="UP"
                        >
                          <path
                            d="M2 25h32L18 9 2 25Z"
                            data-votetype="UP"
                          ></path>
                        </svg>
                      </button>
                      <div
                        id="question-vote-count"
                        className="flex text-[21px] justify-center text-[#6a737c]"
                      >
                        {$fetchData.data && $fetchData.data.data.countingVote}
                      </div>
                      <button
                        id="question-down-button"
                        className="m-[2px]"
                        onClick={handleVoteButtonClick}
                      >
                        <svg
                          width="36"
                          height="36"
                          viewBox="0 0 36 36"
                          fill={voteIconColor}
                          data-votetype="DOWN"
                        >
                          <path
                            d="M2 11h32L18 27 2 11Z"
                            data-votetype="DOWN"
                          ></path>
                        </svg>
                      </button>
                      <div className="flex justify-center py-[4px] mb-[4px]">
                        <svg
                          width="18"
                          height="18"
                          viewBox="0 0 18 18"
                          fill="#babfc5"
                        >
                          <path d="m9 10.6 4 2.66V3H5v10.26l4-2.66ZM3 17V3c0-1.1.9-2 2-2h8a2 2 0 0 1 2 2v14l-6-4-6 4Z"></path>
                        </svg>
                      </div>
                      <div className="flex justify-center py-[4px]">
                        <svg
                          className="hover:fill-[#0a95ff]"
                          width="19"
                          height="18"
                          viewBox="0 0 19 18"
                          fill="#babfc5"
                        >
                          <path d="M3 9a8 8 0 1 1 3.73 6.77L8.2 14.3A6 6 0 1 0 5 9l3.01-.01-4 4-4-4h3L3 9Zm7-4h1.01L11 9.36l3.22 2.1-.6.93L10 10V5Z"></path>
                        </svg>
                      </div>
                    </div>
                  </div>
                  <div
                    id="question-postcell"
                    className="flex flex-col w-[inherit]"
                  >
                    <div className="align-top pr-[16px] flex flex-col w-auto min-w-[0px]">
                      <div id="question-post-body" className="w-full">
                        {$fetchData.data && (
                          <MDEditor.Markdown
                            source={$fetchData.data.data.body}
                          />
                        )}
                      </div>
                      <div className="mt-[24px] mb-[42px]">
                        <ul className="flex">
                          {$fetchData.data &&
                            $fetchData.data.data.tags.map((element, index) => {
                              return (
                                <li className="mr-[6px]" key={index}>
                                  <Tag text={element.tag}></Tag>
                                </li>
                              );
                            })}
                        </ul>
                      </div>

                      <div className="flex">
                        <div className="flex flex-auto justify-between">
                          <div className="flex text-[#6a737c] text-[13px]">
                            <div
                              id="question-share-button"
                              className="mr-[8px]"
                            >
                              <span className="hover:text-[#949ca4]">
                                Share
                              </span>
                            </div>
                            {userId ===
                            ($fetchData.data && $fetchData.data.data.userId) ? (
                              <div
                                id="question-edit-button"
                                className="mr-[8px]"
                              >
                                <Link
                                  to={`${ROUTE_PATH.EDIT_QUESTION}/${questionId}`}
                                  className="hover:text-[#949ca4]"
                                >
                                  Edit
                                </Link>
                              </div>
                            ) : null}
                            <div className="mr-[8px]">
                              <span className="hover:text-[#949ca4] cursor-help">
                                Follow
                              </span>
                            </div>
                            {userId ===
                            ($fetchData.data && $fetchData.data.data.userId) ? (
                              <div className="mr-[8px]">
                                <span
                                  className="hover:text-[#949ca4] cursor-pointer"
                                  onClick={deleteQuestion}
                                >
                                  Delete
                                </span>
                              </div>
                            ) : null}
                          </div>
                          <div
                            id="question-post-signature"
                            className="rounded-sm bg-[#d9ebf7] w-[200px]"
                          >
                            <div className="p-[5px]">
                              <div
                                id="question-user-action-time"
                                className="mt-[1px] mb-[4px] text-[12px] text-[#6a737c]"
                              >
                                {$fetchData.data &&
                                  `asked ${timeForToday(
                                    $fetchData.data.data.createAt
                                  )}`}
                              </div>
                              <div
                                id="question-user-profile-image"
                                className="float-left rounded-[1px]"
                              >
                                <Link to={ROUTE_PATH.MY_PAGE}>
                                  <img
                                    src={basicProfile}
                                    className="w-[32px] h-[32px] object-cover"
                                  ></img>
                                </Link>
                              </div>
                              <div
                                id="question-user-details"
                                className="ml-[40px] w-[calc(100%-40px)] text-[13px]"
                              >
                                <Link
                                  to={ROUTE_PATH.MY_PAGE}
                                  className="text-[#157bce] hover:text-[#0e96ff]"
                                >
                                  {$fetchData.data &&
                                    $fetchData.data.data.displayName}
                                </Link>
                                <div>
                                  <span className="font-[700]">100</span>
                                  <span
                                    title="3 silver badges"
                                    className="ml-[2px] mr-[3px]"
                                  >
                                    <span className="badgecount">3</span>
                                  </span>
                                  <span title="11 bronze badges">
                                    <span className="badgecount">11</span>
                                  </span>
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                    <div
                      id="question-comments"
                      className="pr-[16px] w-auto min-w-[0px]"
                    >
                      <div className="w-full mt-[12px] pb-[10px]">
                        <ul className="text-[13px] border-b-[1px] border-[#e4e6e8]">
                          {$fetchData.data &&
                            $fetchData.data.data.questionComments.map(
                              ({ comment, displayName, createAt }, index) => {
                                return (
                                  <li
                                    key={index}
                                    className="border border-t-[1px] border-[#e4e6e8] py-[10px] flex"
                                  >
                                    <div>
                                      <span>{`${comment} –`}</span>
                                      <span className="mx-[5px] text-[#0375cc]">
                                        {displayName}
                                      </span>
                                      <span className="text-[#9299a1]">
                                        {new Date(createAt).toLocaleString(
                                          'en-US'
                                        )}
                                      </span>
                                    </div>
                                  </li>
                                );
                              }
                            )}
                        </ul>
                      </div>
                      <AddComment questionId={questionId}></AddComment>
                    </div>
                  </div>
                </div>
              </div>

              <div id="answer" className="w-auto float-none pt-[10px]">
                {$fetchData.data && $fetchData.data.data.answers ? (
                  <div
                    id="answers-header"
                    className="w-full mt-[10px] mb-[8px] flex items-center"
                  >
                    <div className="flex-auto">
                      {$fetchData.data &&
                        `${$fetchData.data.data.answers.length} Answers`}
                    </div>
                    <div className="flex text-[12px]">
                      <div className="min-w-[60px] flex items-center mr-[5px]">
                        Sorted by:
                      </div>
                      <div className="w-[100%] border-[1px] border-[#babfc2] flex jusitfy-center items-center rounded-[3px]">
                        <select className="w-full rounded-[3px] pl-[5px] pr-[6px] py-[5px] focus:outline-none focus:border-sky-500 focus:ring-1 focus:ring-sky-300 focus:shadow-md focus:shadow-sky-200">
                          <option value="scoredesc">
                            Highest score (default)
                          </option>
                          <option value="trending">
                            Trending (recent votes count more)
                          </option>
                          <option value="modifieddesc">
                            Date modified (newest first)
                          </option>
                          <option value="createdasc">
                            Date created (oldest first)
                          </option>
                        </select>
                      </div>
                    </div>
                  </div>
                ) : null}
                {$fetchData.data &&
                  $fetchData.data.data.answers.map((answer, index) => {
                    return (
                      <div
                        key={index}
                        className="flex w-full pb-[20px] border-b-[1px] border-[#e4e6e8] mb-[20px]"
                      >
                        <div className="w-auto pr-[16px] align-top">
                          <div className="flex items-stretch justify-center flex-col">
                            <button
                              className="m-[2px]"
                              onClick={handleVoteButtonClick}
                            >
                              <svg
                                width="36"
                                height="36"
                                viewBox="0 0 36 36"
                                fill={voteIconColor}
                                data-votetype="UP"
                                data-answerid={answer.answerId}
                              >
                                <path
                                  d="M2 25h32L18 9 2 25Z"
                                  data-votetype="UP"
                                  data-answerid={answer.answerId}
                                ></path>
                              </svg>
                            </button>
                            <div className="flex text-[21px] justify-center text-[#6a737c]">
                              {answer.countingVote}
                            </div>
                            <button
                              className="m-[2px]"
                              onClick={handleVoteButtonClick}
                            >
                              <svg
                                width="36"
                                height="36"
                                viewBox="0 0 36 36"
                                fill={voteIconColor}
                                data-votetype="DOWN"
                                data-answerid={answer.answerId}
                              >
                                <path
                                  d="M2 11h32L18 27 2 11Z"
                                  data-votetype="DOWN"
                                  data-answerid={answer.answerId}
                                ></path>
                              </svg>
                            </button>
                            <div className="flex justify-center py-[4px] mb-[4px]">
                              <svg
                                width="18"
                                height="18"
                                viewBox="0 0 18 18"
                                fill="#babfc5"
                              >
                                <path d="m9 10.6 4 2.66V3H5v10.26l4-2.66ZM3 17V3c0-1.1.9-2 2-2h8a2 2 0 0 1 2 2v14l-6-4-6 4Z"></path>
                              </svg>
                            </div>
                            {answer.check ? (
                              <div className="flex justify-center">
                                <svg
                                  width="36"
                                  height="36"
                                  viewBox="0 0 36 36"
                                  fill="#2f6f44"
                                >
                                  <path d="m6 14 8 8L30 6v8L14 30l-8-8v-8Z"></path>
                                </svg>
                              </div>
                            ) : null}
                            <div className="flex justify-center py-[4px]">
                              <svg
                                className="hover:fill-[#0a95ff]"
                                width="19"
                                height="18"
                                viewBox="0 0 19 18"
                                fill="#babfc5"
                              >
                                <path d="M3 9a8 8 0 1 1 3.73 6.77L8.2 14.3A6 6 0 1 0 5 9l3.01-.01-4 4-4-4h3L3 9Zm7-4h1.01L11 9.36l3.22 2.1-.6.93L10 10V5Z"></path>
                              </svg>
                            </div>
                          </div>
                        </div>
                        <div className="align-top pr-[16px] flex flex-col w-full min-w-[0px]">
                          <div className="w-full mb-[40px]">
                            <MDEditor.Markdown source={answer.body} />
                          </div>
                          <div className="flex w-full">
                            <div className="flex flex-auto justify-between">
                              <div className="flex text-[#6a737c] text-[13px]">
                                <div>
                                  <span className="mr-[8px] hover:text-[#949ca4]">
                                    Share
                                  </span>
                                </div>
                                {userId === answer.userId ? (
                                  <div>
                                    <Link
                                      to={'' /* 해당 답변 수정 페이지 이동 */}
                                    >
                                      <span className="mr-[8px] hover:text-[#949ca4]">
                                        Edit
                                      </span>
                                    </Link>
                                  </div>
                                ) : null}
                                <div>
                                  <span className="mr-[8px] hover:text-[#949ca4] cursor-help">
                                    Follow
                                  </span>
                                </div>
                                {userId === answer.userId ? (
                                  <div className="mr-[8px]">
                                    <span
                                      className="hover:text-[#949ca4] cursor-pointer"
                                      onClick={deleteAnswer}
                                      data-answerid={answer.answerId}
                                    >
                                      Delete
                                    </span>
                                  </div>
                                ) : null}
                                {userId === $fetchData.data.data.userId &&
                                !isCheckedQuestion ? (
                                  <div className="mr-[8px]">
                                    <span
                                      className="hover:text-[#949ca4] cursor-pointer"
                                      onClick={selectAnswer}
                                      data-answerid={answer.answerId}
                                    >
                                      Select!
                                    </span>
                                  </div>
                                ) : null}
                              </div>
                              <div
                                id="post-signature"
                                className="rounded-sm w-[200px]"
                              >
                                <div id="user-info" className="p-[5px]">
                                  <div
                                    id="user-action-time"
                                    className="mt-[1px] mb-[4px] text-[12px] text-[#6a737c]"
                                  >
                                    {$fetchData.data &&
                                      `answered ${timeForToday(
                                        answer.createAt
                                      )}`}
                                  </div>
                                  <div
                                    id="user-profile-image"
                                    className="float-left rounded-[1px]"
                                  >
                                    <Link to={ROUTE_PATH.MY_PAGE}>
                                      <img
                                        src={basicProfile}
                                        className="w-[32px] h-[32px] object-cover"
                                      ></img>
                                    </Link>
                                  </div>
                                  <div
                                    id="user-details"
                                    className="ml-[40px] w-[calc(100%-40px)] text-[13px]"
                                  >
                                    <Link
                                      to={ROUTE_PATH.MY_PAGE}
                                      className="text-[#157bce] hover:text-[#0e96ff]"
                                    >
                                      {answer.displayName}
                                    </Link>
                                    <div id="scores">
                                      <span
                                        id="reputation score"
                                        className="font-[700]"
                                      >
                                        100
                                      </span>
                                      <span
                                        title="3 silver badges"
                                        className="ml-[2px] mr-[3px]"
                                      >
                                        <span className="badgecount">3</span>
                                      </span>
                                      <span title="11 bronze badges">
                                        <span className="badgecount">11</span>
                                      </span>
                                    </div>
                                  </div>
                                </div>
                              </div>
                            </div>
                          </div>
                          <div className="pr-[16px] w-auto min-w-[0px]">
                            <div className="w-full mt-[12px] pb-[10px]">
                              <ul className="text-[13px] border-b-[1px] border-[#e4e6e8]">
                                {answer.answerComments.map(
                                  ({ comment, displayName, createAt }) => {
                                    return (
                                      <li
                                        key={Math.random()}
                                        className="border border-t-[1px] border-[#e4e6e8] py-[10px] flex"
                                      >
                                        <div>
                                          <span>{`${comment} –`}</span>
                                          <span className="mx-[5px] text-[#0375cc]">
                                            {displayName}
                                          </span>
                                          <span className="text-[#9299a1]">
                                            {new Date(createAt).toLocaleString(
                                              'en-US'
                                            )}
                                          </span>
                                        </div>
                                      </li>
                                    );
                                  }
                                )}
                              </ul>
                            </div>
                            <AddComment answerId={answer.answerId}></AddComment>
                          </div>
                        </div>
                      </div>
                    );
                  })}
              </div>
              <form onSubmit={addAnswer}>
                <h2 className="font-[400] pt-[20px] text-[19px] leading-[1.3]">
                  Your Answer
                </h2>
                <div className="w-full mt-[10px]">
                  <MdArea body={answer} setBody={setAnswer}></MdArea>
                </div>
                <div className="w-[130px] mt-[30px]">
                  <BlueButton text="Post Your Answer"></BlueButton>
                </div>
              </form>
            </div>
            <div className="ml-[10px]">
              <Tips></Tips>
            </div>
          </div>
        </div>
      </div>
      <Footer />
    </>
  );
}

export default QuestionDetail;
