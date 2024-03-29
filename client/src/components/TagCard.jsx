import React from 'react';
import Tag from './Tag';

function TagCard(props) {
  return (
    <div className="flex flex-col border border-[1px]	rounded border-[#d6d9dc] p-[12px]">
      <div className="flex justify-between mb-3">
        <Tag text={props.tag} />
      </div>
      <div className="flex mb-3 text-left line-clamp-4">
        {props.description}
      </div>
      <div className="flex">
        <div className="flex-1 text-[#838c95] text-xs text-left">
          11111 questions
        </div>
        <div className="flex-1 text-[#838c95] text-xs text-left">
          281 asked today, 1620 this week
        </div>
      </div>
    </div>
  );
}

export default TagCard;
