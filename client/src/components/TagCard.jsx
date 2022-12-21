import React from 'react';
import Tag from './Tag';

function TagCard(props) {
  return (
    <div className="flex flex-col border border-solid	rounded border-[#d6d9dc] p-[12px]">
      <div className="flex justify-between mb-3">
        <Tag text={'javascript'} />
      </div>
      <div className="flex text-left mb-3 line-clamp-4">
        Lorem ipsum dolor sit amet consectetur adipisicing elit. Est, minus
        facere iure maxime voluptatibus aspernatur non. Quaerat unde iste
        obcaecati. Expedita repellat architecto ea, tenetur ut quod doloribus
        amet minus. Lorem ipsum dolor sit amet consectetur adipisicing elit.
        Est, minus facere iure maxime voluptatibus aspernatur non. Quaerat unde
        iste obcaecati. Expedita repellat architecto ea, tenetur ut quod
        doloribus amet minus.
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
