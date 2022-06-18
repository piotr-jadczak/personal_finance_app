import RegisterForm from "./components/RegisterForm/RegisterForm";
import LoginForm from "./components/LoginForm/LoginForm";
import 'bootstrap/dist/css/bootstrap.css';
import 'jquery/dist/jquery.min.js';
import 'bootstrap/dist/js/bootstrap.min.js';
import { Routes, Route, useNavigate } from "react-router-dom";
import { useState } from 'react';
import axios from 'axios';
import StartHeader from "./components/StartHeader/StartHeader";
import BoardPage from "./components/BoardPage/BoardPage";
import SavingsPage from "./components/SavingsPage/SavingsPage";
import ExpensesPage from "./components/ExpensesPage/ExpensesPage";
import GoalsPage from "./components/GoalsPage/GoalsPage";
import BoardHeader from "./components/BoardHeader/BoardHeader";

function App() {

  const [jwtToken, setJwtToken] = useState('')
  const [showStartHeader, setShowStartHeader] = useState(true)
  const [showBoardHeader, setShowBoardHeader] = useState(false)
  const [showLoginError, setShowLoginError] = useState(false)

  axios.defaults.baseURL = 'http://localhost:8080/api'

  let navigate = useNavigate()

  const onLogin = async ({ username, password }) => {
    setShowLoginError(false)
    const credentials = {
      username: username,
      password: password
    }
    axios.post('/auth/signin', credentials, {
      headers: { 'Content-Type': 'application/json' }
    }).then(res =>
      handleLoginSuccess(res.data)
    ).catch(err => {
      handleLoginError()
    })
  }

  const handleLoginSuccess = (token) => {
    setJwtToken(token)
    swithMenu()
    navigate("/app");
  }

  const handleLoginError = () => {
    setShowLoginError(true)
  }

  const onLogout = () => {
    setJwtToken('')
    navigate("/")
    swithMenu()
  }


  const swithMenu = () => {
    setShowStartHeader(!showStartHeader)
    setShowBoardHeader(!showBoardHeader)
  }

  return (
    <div className="App">
      {showStartHeader && <StartHeader />}
      {showBoardHeader && <BoardHeader onLogout={onLogout} />}
      <div className="container mt-2">
        <div>
          <Routes>
            <Route path='/' element={<LoginForm onLogin={onLogin} loginError={showLoginError} />} />
            <Route path="/sign-in" element={<LoginForm onLogin={onLogin} loginError={showLoginError} />} />
            <Route path="/sign-up" element={<RegisterForm />} />
            <Route path="/app" element={<BoardPage onLogout={onLogout} jwtToken={jwtToken} />} />
            <Route path="/app/board" element={<BoardPage onLogout={onLogout} jwtToken={jwtToken} />} />
            <Route path="/app/savings" element={<SavingsPage jwtToken={jwtToken} />} />
            <Route path="/app/expenses" element={<ExpensesPage jwtToken={jwtToken} />} />
            <Route path="/app/goals" element={<GoalsPage jwtToken={jwtToken} />} />
          </Routes>
        </div>
      </div>
    </div>
  );
}

export default App;
