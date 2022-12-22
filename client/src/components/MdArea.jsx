import React from 'react';
import MDEditor from '@uiw/react-md-editor';

function MdArea() {
  // markdown.current.value();
  // new StacksEditor(markdown, '*Your* **markdown** here', {});

  const [value, setValue] = React.useState('**Hello world!!!**');
  return (
    <div className="container">
      <MDEditor value={value} onChange={setValue} />
      {/* <MDEditor.Markdown source={value} style={{ whiteSpace: 'pre-wrap' }} /> */}
    </div>
  );
}

export default MdArea;
