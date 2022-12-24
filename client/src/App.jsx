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
import ROUTE_PATH from './constants/routePath';

function App() {
  return (
    <Router basename="/">
      <Navbar />
      <Routes>
        <Route path={ROUTE_PATH.HOME} element={<Home />} />
        <Route path={ROUTE_PATH.LOGIN} element={<Login />} />
        <Route path={ROUTE_PATH.SIGNUP} element={<SignUp />} />
        <Route path={ROUTE_PATH.ADD_QUESTION} element={<CreateQuestion />} />
        <Route path={ROUTE_PATH.EDIT_QUESTION} element={<EditQuestion />} />
        <Route path={ROUTE_PATH.MY_PAGE} element={<MyPage />} />
        <Route path={ROUTE_PATH.TAGS} element={<Tags />} />
        <Route path={ROUTE_PATH.USERS} element={<Users />} />
        <Route path={ROUTE_PATH.DETAIL} element={<QuestionDetail />} />
      </Routes>
    </Router>
  );
}
export default App;
