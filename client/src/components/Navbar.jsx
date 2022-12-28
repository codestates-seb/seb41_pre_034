import React from 'react';
import SkyButton from './buttons/SkyButton';
import BlueButton from './buttons/BlueButton';
import basicProfile from '../assets/basicProfile.png';
import Inputbox from './Inputbox';
import { Link } from 'react-router-dom';
import ROUTE_PATH from '../constants/routePath';

function Navbar(props) {
  const dummyButtonImageColor = '#525960';
  const dummyButtonImageSources = [
    <svg
      width="20"
      height="18"
      viewBox="0 0 20 18"
      fill={dummyButtonImageColor}
    >
      <path d="M4.63 1h10.56a2 2 0 0 1 1.94 1.35L20 10.79V15a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2v-4.21l2.78-8.44c.25-.8 1-1.36 1.85-1.35Zm8.28 12 2-2h2.95l-2.44-7.32a1 1 0 0 0-.95-.68H5.35a1 1 0 0 0-.95.68L1.96 11h2.95l2 2h6Z" />
    </svg>,
    <svg
      width="18"
      height="18"
      viewBox="0 0 18 18"
      fill={dummyButtonImageColor}
    >
      <path d="M15 2V1H3v1H0v4c0 1.6 1.4 3 3 3v1c.4 1.5 3 2.6 5 3v2H5s-1 1.5-1 2h10c0-.4-1-2-1-2h-3v-2c2-.4 4.6-1.5 5-3V9c1.6-.2 3-1.4 3-3V2h-3ZM3 7c-.5 0-1-.5-1-1V4h1v3Zm8.4 2.5L9 8 6.6 9.4l1-2.7L5 5h3l1-2.7L10 5h2.8l-2.3 1.8 1 2.7h-.1ZM16 6c0 .5-.5 1-1 1V4h1v2Z" />
    </svg>,
    <svg
      width="18"
      height="18"
      viewBox="0 0 18 18"
      fill={dummyButtonImageColor}
    >
      <path d="M9 1C4.64 1 1 4.64 1 9c0 4.36 3.64 8 8 8 4.36 0 8-3.64 8-8 0-4.36-3.64-8-8-8Zm.81 12.13c-.02.71-.55 1.15-1.24 1.13-.66-.02-1.17-.49-1.15-1.2.02-.72.56-1.18 1.22-1.16.7.03 1.2.51 1.17 1.23ZM11.77 8c-.59.66-1.78 1.09-2.05 1.97a4 4 0 0 0-.09.75c0 .05-.03.16-.18.16H7.88c-.16 0-.18-.1-.18-.15.06-1.35.66-2.2 1.83-2.88.39-.29.7-.75.7-1.24.01-1.24-1.64-1.82-2.35-.72-.21.33-.18.73-.18 1.1H5.75c0-1.97 1.03-3.26 3.03-3.26 1.75 0 3.47.87 3.47 2.83 0 .57-.2 1.05-.48 1.44Z" />
    </svg>,
    <svg
      width="18"
      height="18"
      viewBox="0 0 18 18"
      fill={dummyButtonImageColor}
    >
      <path d="m8.9844-0.013672a1 1 0 0 0 -0.98438 1.0137v0.38281l-0.55273-0.27734a1 1 0 0 0 -0.48242 -0.11133 1 1 0 0 0 -0.41211 1.9004l1.4473 0.72266v3.6523l-3.1621-1.8262 0.097656-1.6152a1 1 0 0 0 -0.95117 -1.0742 1 1 0 0 0 -1.0449 0.95508l-0.037109 0.61719-0.33008-0.19141a1 1 0 0 0 -0.57422 -0.14062 1 1 0 0 0 -0.42578 1.8711l0.33203 0.19141-0.51758 0.3418a1 1 0 1 0 1.1016 1.668l1.3516-0.89258 3.1621 1.8262-3.1621 1.8262-1.3516-0.89258a1 1 0 0 0 -0.56445 -0.17383 1 1 0 0 0 -0.53711 1.8418l0.51758 0.3418-0.33203 0.19141a1 1 0 1 0 1 1.7305l0.33008-0.19141 0.037109 0.61719a1 1 0 1 0 1.9961 -0.11914l-0.097656-1.6152 3.1621-1.8262v3.6523l-1.4473 0.72266a1 1 0 0 0 0.89453 1.7891l0.55273-0.27734v0.38281a1 1 0 1 0 2 0v-0.38281l0.55273 0.27734a1 1 0 1 0 0.89453 -1.7891l-1.4473-0.72266v-3.6523l3.1621 1.8262-0.097656 1.6152a1 1 0 1 0 1.9961 0.11914l0.037109-0.61719 0.33008 0.19141a1 1 0 1 0 1 -1.7305l-0.33203-0.19141 0.51758-0.3418a1 1 0 0 0 -0.56641 -1.8418 1 1 0 0 0 -0.53516 0.17383l-1.3516 0.89258-3.1621-1.8262 3.1621-1.8262 1.3516 0.89258a1 1 0 1 0 1.1016 -1.668l-0.51758-0.3418 0.33203-0.19141a1 1 0 0 0 -0.45508 -1.8711 1 1 0 0 0 -0.54492 0.14062l-0.33008 0.19141-0.037109-0.61719a1 1 0 0 0 -0.97461 -0.95508 1 1 0 0 0 -1.0215 1.0742l0.097656 1.6152-3.1621 1.8262v-3.6523l1.4473-0.72266a1 1 0 1 0 -0.89453 -1.7891l-0.55273 0.27734v-0.38281a1 1 0 0 0 -1.0156 -1.0137z" />
    </svg>,
    <svg
      width="18"
      height="18"
      viewBox="0 0 18 18"
      fill={dummyButtonImageColor}
    >
      <path d="M15 1H3a2 2 0 0 0-2 2v2h16V3a2 2 0 0 0-2-2ZM1 13c0 1.1.9 2 2 2h8v3l3-3h1a2 2 0 0 0 2-2v-2H1v2Zm16-7H1v4h16V6Z" />
    </svg>,
  ];

  return (
    <>
      <header className="box-border h-[50px] fixed left-0 top-0 min-w-[auto] shadow-md w-full z-[5050] bg-[#ffffff]	flex border-t-[3px] border-[#f48225] items-center">
        <div className="w-[97.2307692rem] max-w-[1264px] h-full flex my-0 mx-auto items-center box-border">
          <Link
            to={ROUTE_PATH.HOME}
            className="py-0 px-2 h-full flex items-center bg-transparent box-border hover:bg-[#e3e6e8]"
          >
            <span className="bg-no-repeat bg-left-bottom ml-0 w-[150px] h-[30px] mt-[-4px] inline-block bg-[url('https://cdn.sstatic.net/Img/unified/sprites.svg?v=fcc0ea44ba27')]"></span>
          </Link>
          <ol className="flex min-w-[236px]">
            {['About', 'Products', 'For Teams'].map((item, index) => {
              return (
                <li key={index} className="m-0 p-0">
                  <a className="text-[#5b6269] py-1.5 px-3 rounded-full shadow-none hover:bg-[#e3e6e8] text-sm cursor-help">
                    {item}
                  </a>
                </li>
              );
            })}
          </ol>
          <div className="py-0 px-[8px] shrink-[10000] grow items-center flex w-full">
            <Inputbox></Inputbox>
          </div>
          <nav className="h-full overflow-x-auto pr-3 ml-auto align-baseline">
            <ol className="flex h-full overflow-x-auto ml-auto list-none items-center min-w-[135px]">
              {props.isLogin ? (
                <>
                  <li className="inline-flex h-full">
                    <Link
                      to={ROUTE_PATH.MY_PAGE}
                      className="inline-flex items-center py-0 px-[12px] relative hover:bg-[#e3e6e8]"
                    >
                      <img
                        src={basicProfile}
                        className="aspect-square rounded"
                        width="24"
                        height="24"
                      ></img>
                    </Link>
                  </li>
                  {dummyButtonImageSources.map((src, index) => (
                    <li key={index} className="inline-flex h-full">
                      <a
                        href="/"
                        className="inline-flex items-center py-0 px-[10px] h-full hover:bg-[#e3e6e8]"
                      >
                        {src}
                      </a>
                    </li>
                  ))}
                </>
              ) : (
                <>
                  <li className="inline-flex">
                    <Link to={ROUTE_PATH.LOGIN}>
                      <SkyButton text={'Log in'}></SkyButton>
                    </Link>
                  </li>
                  <li className="inline-flex px-[4px]">
                    <Link to={ROUTE_PATH.SIGNUP}>
                      <BlueButton text={'Sign up'}></BlueButton>
                    </Link>
                  </li>
                </>
              )}
            </ol>
          </nav>
        </div>
      </header>
    </>
  );
}

export default Navbar;
