import React from 'react'

const RegisterSuccess = ({ message }) => {
    return (
        <div className="alert alert-success" role="alert">
          {message}
        </div>
      )
}

export default RegisterSuccess