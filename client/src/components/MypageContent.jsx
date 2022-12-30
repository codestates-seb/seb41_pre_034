//componet로 구현해 두는 것이 유지보수나 상태관리등에 더 적합하고 편할 것 같아요!
import React, { useEffect, useState } from 'react';
import basicProfile from '../assets/basicProfile.png';
import { BsCircleFill } from 'react-icons/bs';
import { MdCake } from 'react-icons/md';
import { FiClock } from 'react-icons/fi';
import { BiCalendar } from 'react-icons/bi';
import { RiPencilFill } from 'react-icons/ri';
import { BsFillChatSquareTextFill } from 'react-icons/bs';
import OrangeButton from './buttons/OrangeButton';
import { timeForToday } from '../util/timeForToday';
import { useSelector } from 'react-redux';
import { Link } from 'react-router-dom';
import Tag from '../components/Tag';
import BASE_URL from '../constants/baseUrl';

function MypageContent() {
  const [userInfo, setUserInfo] = useState(null);
  const [tags, setTags] = useState([]);
  const userId = useSelector((state) => state.userIdReducer);

  useEffect(() => {
    fetch(BASE_URL + '/users/' + userId, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        Authorization: localStorage.getItem('Authorization'),
        Refresh: localStorage.getItem('Refresh'),
      },
    })
      .then((response) => response.json())
      .then(({ data }) => {
        setUserInfo(data);
        setTags(data.questions.map((el) => el.tags).flat());
      });
  }, []);

  return (
    <div>
      <div className="relativ w-[1051px] h-[144px] mb-[16px]">
        <div className="box-content flex flex-wrap items-center w-[1067px] h-[144px] m-[-8px]">
          <a className=" m-[8px]">
            <img
              src={basicProfile}
              className="w-[128px] h-[128px]  rounded-md"
            ></img>
          </a>

          <div className=" w-[495.445px] h-[68px] m-[8px]">
            <div className="flex items-center text-[34px] w-[421.328px] h-[50pxpx] mb-[-4px]">
              <div className="w-[104.430px] h-[44px] m-[4px] mb-[14px]">
                {userInfo && userInfo.displayName}
              </div>
            </div>
            <ul className="flex text-[13px] text-[#6A737C] w-[600px] h-[26px] ml-[-4px]">
              <li className="flex items-center m-[4px]">
                <div className="mx-[2px]">
                  <MdCake className="w-[18px] h-[18px] " />
                </div>
                <div>
                  {'Member for ' + timeForToday(userInfo && userInfo.createAt)}
                </div>
              </li>
              <li className="flex items-center m-[4px]">
                <div className="mx-[2px]">
                  <FiClock className="w-[18px] h-[18px]" />
                </div>
                <div>Last seen this week</div>
              </li>
              <li className="flex items-center m-[4px]">
                <div className="mx-[2px]">
                  <BiCalendar className="w-[18px] h-[18px]" />
                </div>
                <button>Visited 5 days, 5 consecutive</button>
              </li>
            </ul>
          </div>
        </div>
        <div className="absolute top-0 right-0 flex m-6">
          <a className="flex box-content justify-center items-center rounded-[3px] text-[#6a737c] hover:bg-[#f8f9f9] hover:text-[#525960] border-[#9fa6ad] w-[82.589px] h-[13.831px] p-[9.6px] border-[1px] m-[3px] text-[12px]">
            <RiPencilFill className=" w-[18px] h-[18px] mr-[2px]" />
            Edit profile
          </a>
          <a className="flex box-content justify-center items-center rounded-[3px] text-[#6a737c] hover:bg-[#f8f9f9] hover:text-[#525960] border-[#9fa6ad] w-[104.730px] h-[13.831px] p-[9.6px] border-[1px] m-[3px] text-[12px]">
            <BsFillChatSquareTextFill className="w-[15px] h-[15px] mr-[2px]" />
            Network profile
          </a>
        </div>
      </div>
      <div className="flex text-[13px] w-[1051px] h-[33px] items-center justify-between flex-wrap mb-[16px]">
        <div className="py-[2px] gap-2 text-[#525960]">
          <OrangeButton text="profile"></OrangeButton>
          <OrangeButton text="Activity"></OrangeButton>
          <OrangeButton text="Saves"></OrangeButton>
          <OrangeButton text="Settings"></OrangeButton>
        </div>
        <div className="flex ml-[auto]"></div>
      </div>
      <div className="flex">
        <div className="w-[244.750px] h-[735.383px] m-[12px] flex flex-col gap-4">
          <div>
            <div className="text-[21px] h-[27.453px] w-[244.750px] mb-[8px]">
              Stats
            </div>
            <div className="border-[1px] rounded-md p-[12px] text-[13px] border-[#D6D9DC] text-[#6A737C]">
              <div className="flex flex-wrap w-[234.750px] h-[110.438px] m-[-8px]">
                <div className="w-[101.375px] h-[39.227px] m-[8px]">
                  <div className="text-[17px] text-[#000000]">1</div>reputation
                </div>
                <div className="w-[101.375px] h-[39.227px] m-[8px]">
                  <div className="text-[17px] text-[#000000]">0</div>reached
                </div>
                <div className="w-[101.375px] h-[39.227px] m-[8px]">
                  <div className="text-[17px] text-[#000000]">
                    {userInfo && userInfo.answers.length}
                  </div>
                  answers
                </div>
                <div className="w-[101.375px] h-[39.227px] m-[8px]">
                  <div className="text-[17px] text-[#000000]">
                    {userInfo && userInfo.questions.length}
                  </div>
                  questions
                </div>
              </div>
            </div>
          </div>
          <div>
            <div className="flex justify-between h-[27.453px] mb-[8px] ">
              <div className="text-[21px]">Communities</div>
              <div className="text-[13px] flex items-center text-[#6A737C]">
                Edit
              </div>
            </div>
            <div className="box-content flex flex-wrap items-center text-[#6A737C] border-[#D6D9DC] border-[1px] rounded-md w-[218.750px] h-[29px] p-[12px]">
              <ul>
                <li>
                  <a className="flex items-center text-[13px]">
                    <div>
                      <img
                        src="https://upload.wikimedia.org/wikipedia/commons/thumb/e/ef/Stack_Overflow_icon.svg/768px-Stack_Overflow_icon.svg.png"
                        className="w-[16px] h-[16px] m-[2px]"
                      ></img>
                    </div>
                    <div className="grow pr-[4px] mx-[2px] w-[178.688px] text-[#0074cc]">
                      Stack Overflow
                    </div>
                    <div>1</div>
                  </a>
                </li>
              </ul>
            </div>
          </div>
        </div>
        <div className="w-[782.240px] h-[735.383px] m-[12px] flex flex-col gap-4">
          <div>
            <div className="w-[782.250px] h-[27.453px] mb-[8px] text-[21px]">
              Answers
            </div>
            <div className="box-content w-[780.250px] h-[150px] overflow-y-scroll border-[1px] border-[#D6D9DC] rounded-md bg-[#F8F9F9]">
              {userInfo && userInfo.answers.length === 0 ? (
                <p className="text-[13px] w-[316px] h-full flex justify-center items-center mx-[auto] text-[#6A737C]">
                  You have not answered any questions
                </p>
              ) : (
                userInfo &&
                userInfo.answers.map((el) => (
                  <div
                    key={el.answerId}
                    className="first:rounded-t-md last:rounded-b-md w-full h-[40px] flex justify-between items-center bg-[#ffff] border-[1px] border-[#D6D9DC]"
                  >
                    <div className="flex justify-evenly w-[500px]">
                      <div className="text-center text-[13px] mx-[2px] w-[40px] border-[1px] border-[#D6D9DC]">
                        {el.countingVote}
                      </div>
                      <div className="line-clamp-1 text-[13px] w-[400px] mx-[2px]">
                        {el.body}
                      </div>
                    </div>
                    <div className="text-[13px] mx-[10px]">
                      {new Date(el.createAt).toDateString()}
                    </div>
                  </div>
                ))
              )}
            </div>
          </div>
          <div>
            <div className="w-[782.250px] h-[27.453px] mb-[8px] text-[21px]">
              Questions
            </div>
            <div className="box-content h-[150px] overflow-y-scroll w-[780.250px] border-[1px] border-[#D6D9DC] rounded-md bg-[#F8F9F9]">
              {userInfo && userInfo.questions.length === 0 ? (
                <p className="text-[13px] w-[316px] h-full flex justify-center items-center text-center mx-[auto] text-[#6A737C]">
                  You have not asked any questions
                </p>
              ) : (
                userInfo &&
                userInfo.questions.map((el) => (
                  <div
                    key={el.questionId}
                    className="first:rounded-t-md last:rounded-b-md w-full h-[40px] flex justify-between items-center bg-[#ffff] border-[1px] border-[#D6D9DC]"
                  >
                    <div className="flex justify-evenly w-[500px]">
                      {el.questionStatus === 'OPENED' ? (
                        <div className="text-center rounded-[3px] text-[13px] mx-[2px] w-[40px] border-[1px] border-[#D6D9DC]">
                          {el.answerCounting}
                        </div>
                      ) : (
                        <div className="text-center text-[13px] mx-[2px] w-[40px] rounded-[3px] bg-[#2f6f44] border-[1px] border-[#2f6f44] text-[#ffffff]">
                          {el.answerCounting}
                        </div>
                      )}
                      <Link
                        to={`/detail/${el.questionId}`}
                        className="line-clamp-1 text-[13px] w-[400px] mx-[2px]"
                      >
                        {el.title}
                      </Link>
                    </div>
                    <div className="text-[13px] mx-[10px]">
                      {new Date(el.createAt).toDateString()}
                    </div>
                  </div>
                ))
              )}
            </div>
          </div>
          <div>
            <div className="w-[782.250px] h-[27.453px] mb-[8px] text-[21px]">
              Tags
            </div>
            <div className="box-content w-[780.250px] h-[300px] overflow-y-scroll border-[1px] border-[#D6D9DC] rounded-md bg-[#F8F9F9]">
              {tags && tags.length === 0 ? (
                <p className="text-[13px] w-[316px] h-full flex justify-center items-center text-center mx-[auto] text-[#6A737C]">
                  You have not participated in any tags
                </p>
              ) : (
                tags &&
                tags.map((el, index) => (
                  <div
                    key={index}
                    className="first:rounded-t-md last:rounded-b-md w-full h-[40px] flex justify-between items-center bg-[#ffff] border-[1px] border-[#D6D9DC]"
                  >
                    <div className="ml-[30px] w-[100px] h-[30px] flex items-center justify-start">
                      <Tag text={el.tag} />
                      <BsCircleFill className="ml-[5px] text-[5px] text-[#fdc057fc]" />
                    </div>
                    <div className="flex justify-between">
                      <div className="text-[13px] mx-[10px]">11,632 score</div>
                      <div className="text-[13px] mx-[10px]">5,546 posts</div>
                    </div>
                  </div>
                ))
              )}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default MypageContent;
