import React from 'react';
import MDEditor from '@uiw/react-md-editor';

function MdArea({ body, setBody }) {
  function handleChange(value) {
    setBody(value);
    console.log(body);
  }

  return (
    <div className="container">
      <MDEditor value={body} onChange={handleChange} />
    </div>
  );
}

export default MdArea;
