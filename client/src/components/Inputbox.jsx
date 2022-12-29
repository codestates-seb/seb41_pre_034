import React, { useState } from 'react';
import { AiOutlineSearch } from 'react-icons/ai';
import { useDispatch } from 'react-redux';
import ROUTE_PATH from '../constants/routePath';
import { search } from '../redux/actions/index';

function Inputbox() {
  const [searchValue, setSearchValue] = useState('');
  const dispatch = useDispatch();

  function handleChange(e) {
    setSearchValue(e.target.value);
    console.log(searchValue);
  }

  //검색어를 submit 하면...searchValue에 해당하는 값을 잘 빼서 get요청 url 파라미터로 붙여줘야 한다
  function handleSubmit(e) {
    e.preventDefault();
    //dispatch 불러와서 전역 변수로 넣음
    dispatch(search(searchValue));
    window.location.href = ROUTE_PATH.SEARCH;
  }
  return (
    <form onSubmit={handleSubmit} className="w-full">
      <div className="w-[100%] border-[1px] border-[#babfc2] flex jusitfy-center items-center rounded-[3px]">
        <AiOutlineSearch className="text-[30px] text-[#929aa2] ml-1" />
        <input
          className="placeholder-[#9ea5ac] text-[15px] w-[100%] rounded-[3px] pl-[10px] pr-[6px] py-[5px] focus:outline-none focus:border-sky-500 focus:ring-1 focus:ring-sky-300 focus:shadow-md focus:shadow-sky-200"
          placeholder="Search..."
          onChange={handleChange}
        ></input>
      </div>
    </form>
  );
}

export default Inputbox;
