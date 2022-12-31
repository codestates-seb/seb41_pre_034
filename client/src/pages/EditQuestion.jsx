import React, { useEffect, useState } from 'react';
import MdArea from '../components/MdArea';
import BlueButton from '../components/buttons/BlueButton';
import Footer from '../components/Footer';
import Tag from '../components/Tag';
import { useParams } from 'react-router-dom';
import { useSelector } from 'react-redux';
import ROUTE_PATH from '../constants/routePath';
import { fetchPatch } from '../util/api';
import handleAuthError from '../exception/handleAuthError';

function EditQuestion() {
  const { questionId } = useParams();
  const [title, setTitle] = useState('');
  const [body, setBody] = useState('');
  const [tags, setTags] = useState([]);
  const userId = useSelector((state) => state.userIdReducer);

  useEffect(() => {
    fetch(process.env.REACT_APP_BASE_URL + '/questions/' + questionId)
      .then((response) => response.json())
      .then(({ data }) => {
        setTitle(data.title);
        setBody(data.body);
        setTags(data.tags.map((tag) => tag.tag));
      });
  }, []);

  function removeTags(indexToRemove) {
    setTags(tags.filter((el, index) => index !== indexToRemove));
  }

  function addTags(event) {
    event.preventDefault();

    const inputValue = event.target.value.trim();
    event.target.value = inputValue;

    if (
      inputValue.length !== 0 &&
      !tags.includes(inputValue) &&
      event.key === ' '
    ) {
      setTags([...tags, inputValue]);
      event.target.value = '';
    }
  }

  async function handleSubmit(event) {
    event.preventDefault();

    const data = {
      title,
      body,
      userId,
      tags: tags.join(','),
    };
    const response = await fetchPatch('/questions/', questionId, data);

    if (title.trim().length === 0) {
      alert('제목을 입력해주세요.');

      return;
    }

    if (body.trim().length === 0) {
      alert('본문을 입력해주세요.');

      return;
    }

    if (response.ok) {
      window.location.href = `${ROUTE_PATH.DETAIL}/${questionId}`;

      return;
    }

    if (response.status >= 400 && response.status < 500) {
      handleAuthError(response.status, handleSubmit);
    }
  }

  return (
    <div className="max-w-[100%] flex flex-col justify-center items-center ">
      <div className="mt-[50px] px-[24px] pb-[24px] max-w-[1264px]">
        <div className="mx-[16px] mb-[12px] flex justify-between">
          <span className="my-[27px] text-[27px] font-[700] w-[280px] h-[28.594px]">
            Edit question
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
        <form
          onSubmit={handleSubmit}
          className="flex flex-col justify-center item-start"
        >
          <div className="p-[24px] m-[16px] w-[70%] flex flex-col border-[1px] border-[#e5e7e8] rounded-[3px]">
            <label htmlFor="title" className="font-[600] my-[2px]">
              Title
            </label>
            <input
              value={title ?? ''}
              onChange={({ target }) => setTitle(target.value)}
              type="text"
              id="title"
              placeholder="e.g Is there an R function for finding the index of an element in a vector"
              className="border-[1px] border-[#e5e7e8] my-[2px] py-[7.8px] px-[9.1px] text-[13px] rounded-[3px] h-[32px] focus:outline-none focus:border-sky-500 focus:ring-1 focus:ring-sky-300 focus:shadow-md focus:shadow-sky-200"
            ></input>
            <div className="my-[20px]">
              <p className="font-[600] my-[4px]">
                What are the details of your problem?
              </p>
              <MdArea body={body ?? ''} setBody={setBody} />
            </div>
            <label htmlFor="tags" className="font-[600] my-[2px]">
              Tags
            </label>
            <div className="flex items-center border-[1px] rounded-[3px] border-[#e5e7e8] text-[13px] h-[37px] focus-within:outline-none focus-within:border-sky-500 focus-within:ring-1 focus-within:ring-sky-300 focus-within:shadow-md focus-within:shadow-sky-200">
              <ul className="flex ml-[8px]">
                {tags.map((tag, index) => (
                  <li
                    key={index}
                    className="group/tag w-[auto] h-[30px] flex items-center justify-center bg-[#e1ecf4] hover:bg-[#d0e3f1] mx-[2px] rounded-[5px]"
                  >
                    <Tag text={tag} />
                    <span
                      onClick={() => removeTags(index)}
                      className="block w-[20px] h-[20px] text-[#39739d] font-[700] text-center cursor-pointer hover:bg-[#39739d] hover:rounded-[3px] hover:text-[#e1ecf4]"
                    >
                      x
                    </span>
                  </li>
                ))}
              </ul>
              <input
                id="tags"
                onKeyUp={addTags}
                placeholder="e.g.(ajax wpf sql)"
                className="flex-auto my-[2px] py-[7.8px] h-[20px] px-[9.1px] text-[13px] rounded-[3px] focus:ring-0 focus:outline-none"
              ></input>
            </div>
          </div>
          <div className="w-[250px] h-[60px] mt-[12px] m-[16px] p-[10.4px]">
            <BlueButton text="Edit your question" />
          </div>
        </form>
      </div>
      <Footer />
    </div>
  );
}

export default EditQuestion;
