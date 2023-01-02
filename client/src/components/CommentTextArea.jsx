import React, { useState } from 'react';
import { useSelector } from 'react-redux';
import { fetchPost } from '../util/api';
import BlueButton from './buttons/BlueButton';

function CommentTextArea({ questionId, answerId }) {
  const [comment, setComment] = useState('');
  const userId = useSelector((state) => state.userIdReducer);

  function handleTextAreaChange({ target }) {
    setComment(target.value);
  }

  async function handlePostCommentSubmit() {
    const url = questionId
      ? `/question-comment/${questionId}`
      : `/answer-comment/${answerId}`;
    const data = {
      comment,
      userId,
    };

    const response = await fetchPost(url, data);
    if (response.status >= 400 && response.status < 500) {
      handleAuthError(response.status, handlePostCommentSubmit);
    }
  }

  return (
    <form onSubmit={handlePostCommentSubmit} className="w-full">
      <div className="w-[100%] border-[1px] border-[#babfc2] flex jusitfy-center items-center rounded-[3px]">
        <textarea
          value={comment}
          onChange={handleTextAreaChange}
          className="placeholder-[#9ea5ac] text-[15px] h-[100px] w-[100%] rounded-[3px] pl-[10px] pr-[6px] py-[5px] focus:outline-none focus:border-sky-500 focus:ring-1 focus:ring-sky-300 focus:shadow-md focus:shadow-sky-200"
        ></textarea>
      </div>
      <div className="my-[10px] w-[200px]">
        <BlueButton text="Post Your Comment"></BlueButton>
      </div>
    </form>
  );
}

export default CommentTextArea;
