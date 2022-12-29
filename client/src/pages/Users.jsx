import React from 'react';
import Sidebar from '../components/Sidebar';
import Footer from '../components/Footer';
import Inputbox from '../components/Inputbox';
import jinwon from '../assets/jinwon.png';
import minkyung from '../assets/minkyung.JPG';
import suyoung from '../assets/suyoung.jpeg';
import choiyu from '../assets/choiyu.jpeg';
import wondo from '../assets/wondo.png';
import seonghun from '../assets/seonghun.jpeg';
import Tabs from '../components/Tabs';

function Users() {
  const UserCard = (props) => {
    return (
      <div className=" mb-[10px] pt-[5px] pb-[8px] pl-[7px] pr-[6px] w-[240.750px] h-[85.469px] flex">
        <div>
          <img src={props.img} className="w-[48px] h-[48px]"></img>
        </div>
        <div className="flex flex-col ml-[9px]">
          <span className="text-[15px] text-[#1880d0]">{props.name}</span>
          <span className="text-[12px] text-[#8e949c]">{props.country}</span>
          <span className="text-[12px] text-[#6b747c] font-[700]">
            {props.reputation}
          </span>
          <span className="text-[12px] text-[#1880d0]">{props.language}</span>
        </div>
      </div>
    );
  };
  return (
    <div className="mt-[50px] max-w-[100%] flex flex-col justify-center items-center">
      <div className="container mt-0 max-w-[1264px] w-full flex justify-between mx-auto my-0 relative z-[1000] flex-[1_0_auto] text-left min-h-[calc(100vh-50px-322px)]">
        <Sidebar currentMenu="Users"></Sidebar>
        <div className="content max-w-[1100px] w-[calc(100%-164px)] p-[24px] border-l-[1px] border-[#e1e2e5]">
          <div className="text-[27px] font-[500] w-[280px] h-[28.594px] mb-[24px]">
            Users
          </div>
          <div className="flex justify-between">
            <div className="w-[188.59px] h-[32.8px] mb-[52px] pt-[2px]">
              <Inputbox />
            </div>
            <div>
              <Tabs
                menuArr={[
                  { name: 'Reputation' },
                  { name: 'New users' },
                  { name: 'Voters' },
                  { name: 'Editors' },
                  { name: 'Moderators' },
                ]}
              />
            </div>
          </div>
          <div className="h-[100vh]">
            <div className="flex flex-wrap">
              <UserCard
                name="Minkyung"
                img={minkyung}
                country="south korea"
                reputation="153,112"
                language="javaScript, React"
              />
              <UserCard
                name="Jinwon"
                img={jinwon}
                country="south korea"
                reputation="153,112"
                language="javaScript, React"
              />
              <UserCard
                name="Seonghun"
                img={seonghun}
                country="south korea"
                reputation="153,112"
                language="javaScript, React"
              />
              <UserCard
                name="Suyoung"
                img={suyoung}
                country="south korea"
                reputation="153,112"
                language="java, Spring"
              />
              <UserCard
                name="Choiyu"
                img={choiyu}
                country="south korea"
                reputation="153,112"
                language="java, Spring"
              />
              <UserCard
                name="Wondo"
                img={wondo}
                country="south korea"
                reputation="153,112"
                language="java, Spring"
              />
            </div>
          </div>
        </div>
      </div>
      <Footer />
    </div>
  );
}

export default Users;
