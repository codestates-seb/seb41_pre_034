import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Home from './pages/Home';
import Login from './pages/Login';
import SignUp from './pages/SignUp';
import CreateQuestion from './pages/CreateQuestion';
import EditQuestion from './pages/EditQuestion';
import MyPage from './pages/MyPage';
import Tags from './pages/Tags';
import Users from './pages/Users';
import QuestionDetail from './pages/QuestionDetail';
import Navbar from './components/Navbar';

function App() {
  return (
    <Router basename="/">
      <Navbar />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<SignUp />} />
        <Route path="/addquestion" element={<CreateQuestion />} />
        <Route path="/editquestion" element={<EditQuestion />} />
        <Route path="/mypage" element={<MyPage />} />
        <Route path="/tags" element={<Tags />} />
        <Route path="/Users" element={<Users />} />
        <Route path="/detail" element={<QuestionDetail />} />
      </Routes>
    </Router>
  );
}
export default App;
