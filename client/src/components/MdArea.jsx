import React from 'react';
import MDEditor from '@uiw/react-md-editor';

function MdArea() {
  const [value, setValue] = React.useState('**Hello world!!!**');
  return (
    <div className="container">
      <MDEditor value={value} onChange={setValue} />
    </div>
  );
}

export default MdArea;
