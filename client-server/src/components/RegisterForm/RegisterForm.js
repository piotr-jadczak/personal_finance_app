import { useState } from 'react'
import RegisterSuccess from './RegisterSuccess'
import axios from 'axios';
import RegisterError from './RegisterError';
import './registerForm.css'
import { BsPersonCircle } from 'react-icons/bs'

const RegisterForm = () => {
  const [username, setUsername] = useState('')
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [showError, setShowError] = useState(false)
  const [showSuccess, setShowSuccess] = useState(false)
  const [message, setMessage] = useState('')
  
  const onSubmit = (e) => {
    e.preventDefault()

    const userToRegister = {
      username: username,
      email: email,
      password: password
    }
    
    axios.post('/auth/signup', userToRegister, {
      headers: { 'Content-Type': 'application/json' }
      }).then(res => 
        handleSuccess(res.data)
      ).catch(err => {
        handleError(err.response)
    })

    setUsername('')
    setEmail('')
    setPassword('')
  }

  const handleSuccess = (data) => {
    setShowSuccess(false)
    setShowError(false)
    setMessage(data.message)
    setShowSuccess(true)
  }

  const handleError = (response) => {
    setShowSuccess(false)
    setShowError(false)
    setMessage(response.data.message)
    setShowError(true)
  }

  return (
    <div className='d-flex justify-content-center mt-5'>
      <div className='bg-light sing-form rounded p-4 shadow mb-5 rounded'>
        <form onSubmit={onSubmit} >
          <div className='sign-title'>
            <a>Register</a><br/>
            <BsPersonCircle size={48} />
          </div>
          <div className="form-group">
            <label className="form-label">Username</label>
            <input 
              type="text"
              className="form-control"
              placeholder="username"
              minLength={4}
              maxLength={20}
              required={true}
              value={username}
              onChange={(e) => setUsername(e.target.value)} 
            />
          </div>
          <div className="form-group">
            <label className="form-label">Email</label>
            <input 
              type="email"
              className="form-control"
              placeholder="email"
              maxLength={64}
              required={true}
              value={email}
              onChange={(e) => setEmail(e.target.value)} 
            />
          </div>
          <div className="form-group">
            <label className="form-label">Password</label>
            <input 
              type="password"
              className="form-control"
              placeholder="password"
              minLength={8}
              maxLength={40}
              required={true}
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
          </div>
          <div className="form-group pt-2">
            <button type="submit" className="btn btn-dark form-button" >Register</button>
          </div>
          {showSuccess && <RegisterSuccess message={message} /> }
          {showError && <RegisterError message={message} /> }
        </form>
      </div>
    </div>
  );
}

export default RegisterForm