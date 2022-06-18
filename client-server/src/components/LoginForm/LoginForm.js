import { useEffect, useState } from 'react'
import './loginForm.css'
import { BsPersonCircle } from 'react-icons/bs'
import LoginError from './LoginError'

const LoginForm = ({ onLogin, loginError }) => {

  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  const loginErrorMessage = "wrong username or password"
  const [showError, setShowError] = useState('loginError')

  useEffect(() => {
    setShowError(loginError)
  }, [loginError])

  const onSubmit = (e) => {
    e.preventDefault()

    onLogin({ username, password })

    setUsername('')
    setPassword('')
  }

  return (
    <div className='d-flex justify-content-center mt-5'>
      <div className='bg-light sing-form rounded p-4 shadow mb-5 rounded'>
        <form onSubmit={onSubmit} >
          <div className='sign-title'>
            <a className="form-label">Login</a><br />
            <BsPersonCircle size={48} />
          </div>
          <div className="form-group">
            <label>Username</label>
            <input
              type="text"
              className="form-control"
              placeholder="username"
              required={true}
              value={username}
              onChange={(e) => setUsername(e.target.value)}
            />
          </div>
          <div className="form-group">
            <label className="form-label" >Password</label>
            <input
              type="password"
              className="form-control"
              placeholder="password"
              required={true}
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
          </div>
          <div className="form-group pt-2">
            <button type="submit" className="btn btn-dark form-button" >Login</button>
          </div>
          {showError && <LoginError message={loginErrorMessage} />}
        </form>
      </div>
    </div>
  );
}

export default LoginForm